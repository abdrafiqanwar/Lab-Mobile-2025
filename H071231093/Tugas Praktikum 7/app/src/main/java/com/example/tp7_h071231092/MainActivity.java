package com.example.tp7_h071231092;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp7_h071231092.adapter.NotesAdapter;
import com.example.tp7_h071231092.ui.FormActivity;
import com.example.tp7_h071231092.util.MappingHelper;
import com.example.tp7_h071231092.data.Notes;
import com.example.tp7_h071231092.util.NotesHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private NotesAdapter adapter;
    private NotesHelper notesHelper;
    private TextView noData;
    private final int REQUEST_ADD = 100;
    private final int REQUEST_UPDATE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNotes = findViewById(R.id.rv_notes);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        noData = findViewById(R.id.noData);
        SearchView searchView = findViewById(R.id.search_view); // Use androidx.appcompat.widget.SearchView

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(this);
        rvNotes.setAdapter(adapter);

        notesHelper = NotesHelper.getInstance(getApplicationContext());
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        // Add listener for SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchNotes(query); // Call searchNotes on submit
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchNotes(newText); // Call searchNotes on text change
                return true;
            }
        });

        loadNotes();
    }

    private void loadNotes() {
        new LoadNotesAsync(this, notes -> {
            if (notes.size() > 0) {
                adapter.setNotes(notes);
                noData.setVisibility(View.GONE); // Sembunyikan pesan "No Data"
            } else {
                adapter.setNotes(new ArrayList<>());
                noData.setVisibility(View.VISIBLE); // Tampilkan pesan "No Data"
                showToast("No data available");
            }
        }).execute();
    }

    private void searchNotes(String query) {
        notesHelper.open();
        Cursor cursor = notesHelper.searchByTitle(query);
        ArrayList<Notes> notes = MappingHelper.mapCursorToArrayList(cursor);
        cursor.close();
        notesHelper.close();

        if (notes.size() > 0) {
            adapter.setNotes(notes);
            noData.setVisibility(View.GONE); // Sembunyikan pesan "No Data"
        } else {
            adapter.setNotes(new ArrayList<>());
            noData.setVisibility(View.VISIBLE); // Tampilkan pesan "No Data"
            showToast("No results found");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD) {
            if (resultCode == FormActivity.RESULT_ADD) {
                showToast("Note added successfully");
                loadNotes();
            }
        } else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == FormActivity.RESULT_UPDATE) {
                showToast("Note updated successfully");
                loadNotes();
            } else if (resultCode == FormActivity.RESULT_DELETE) {
                showToast("Note deleted successfully");
                loadNotes();
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notesHelper != null) {
            notesHelper.close();
        }
    }

    private static class LoadNotesAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadStudentsCallback> weakCallback;

        private LoadNotesAsync(Context context, LoadStudentsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                Context context = weakContext.get();
                if (context != null) {
                    NotesHelper notesHelper = NotesHelper.getInstance(context);
                    notesHelper.open();
                    Cursor studentsCursor = notesHelper.queryAll();
                    ArrayList<Notes> notes = MappingHelper.mapCursorToArrayList(studentsCursor);
                    studentsCursor.close();
                    handler.post(() -> {
                        LoadStudentsCallback callback = weakCallback.get();
                        if (callback != null) {
                            callback.postExecute(notes);
                        }
                    });
                }
            });
        }

        interface LoadStudentsCallback {
            void postExecute(ArrayList<Notes> notes);
        }
    }
}