package com.example.praktikum_8.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.praktikum_8.R;
import com.example.praktikum_8.database.DatabaseHelper;
import com.example.praktikum_8.database.NoteHelper;
import com.example.praktikum_8.note.Note;
import com.example.praktikum_8.note.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {
    private RecyclerView rvNotes;
    private NoteAdapter adapter;
    private NoteHelper noteHelper;
    private TextView noData;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private final int REQUEST_ADD = 100;
    private final int REQUEST_UPDATE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pancing supaya database dibuat dan terlihat di App Inspection
        new DatabaseHelper(this).getWritableDatabase();

        // Inisialisasi komponen
        rvNotes = findViewById(R.id.rv_notes);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        noData = findViewById(R.id.noData);
        searchView = findViewById(R.id.search);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        // Atur warna indikator SwipeRefreshLayout
        swipeRefreshLayout.setColorSchemeColors(0xFF3F51B5); // Warna #3F51B5 (Indigo)

        // Inisialisasi RecyclerView
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(this);
        rvNotes.setAdapter(adapter);

        // Inisialisasi database
        noteHelper = NoteHelper.getInstance(getApplicationContext());

        // Klik FAB untuk tambah catatan
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        // Pull to refresh
        swipeRefreshLayout.setOnRefreshListener(this::loadNotes);

        // Muat catatan saat awal
        loadNotes();

        // Search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchNotes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    loadNotes();
                } else {
                    searchNotes(newText);
                }
                return true;
            }
        });
    }

    private void loadNotes() {
        swipeRefreshLayout.setRefreshing(true);
        new LoadNotesAsync(this, notes -> {
            adapter.setNotes(notes);
            if (notes.size() > 0) {
                noData.setVisibility(View.GONE);
            } else {
                noData.setVisibility(View.VISIBLE);
                showToast("Tidak ada catatan tersedia.");
            }
            swipeRefreshLayout.setRefreshing(false);
        }).execute();
    }

    private void searchNotes(String query) {
        swipeRefreshLayout.setRefreshing(true);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            NoteHelper helper = NoteHelper.getInstance(getApplicationContext());
            helper.open();
            Cursor cursor = helper.searchByJudul(query);
            ArrayList<Note> result = MappingHelper.mapCursorToArrayList(cursor);
            cursor.close();
            helper.close();

            handler.post(() -> {
                adapter.setNotes(result);
                if (result.size() > 0) {
                    noData.setVisibility(View.GONE);
                } else {
                    noData.setVisibility(View.VISIBLE);
                    showToast("Tidak ada catatan yang cocok dengan pencarian Anda.");
                }
                swipeRefreshLayout.setRefreshing(false);
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_ADD && resultCode == FormActivity.RESULT_ADD) {
                showToast("Catatan berhasil ditambahkan.");
                loadNotes();
            } else if (requestCode == REQUEST_UPDATE) {
                if (resultCode == FormActivity.RESULT_UPDATE) {
                    showToast("Catatan berhasil diperbarui.");
                    loadNotes();
                } else if (resultCode == FormActivity.RESULT_DELETE) {
                    showToast("Catatan berhasil dihapus.");
                    loadNotes();
                }
            }
        } else {
            loadNotes();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static class LoadNotesAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallback> weakCallback;

        private LoadNotesAsync(Context context, LoadNotesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                Context context = weakContext.get();
                if (context != null) {
                    NoteHelper noteHelper = NoteHelper.getInstance(context);
                    noteHelper.open();
                    Cursor notesCursor = noteHelper.queryAll();
                    ArrayList<Note> notes = MappingHelper.mapCursorToArrayList(notesCursor);
                    notesCursor.close();
                    noteHelper.close();

                    handler.post(() -> {
                        LoadNotesCallback callback = weakCallback.get();
                        if (callback != null) {
                            callback.postExecute(notes);
                        }
                    });
                }
            });
        }
    }

    interface LoadNotesCallback {
        void postExecute(ArrayList<Note> notes);
    }
}
