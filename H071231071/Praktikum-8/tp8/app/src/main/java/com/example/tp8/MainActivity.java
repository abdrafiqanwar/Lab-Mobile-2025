//package com.example.tp8;
//
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import org.jetbrains.annotations.Nullable;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class MainActivity extends AppCompatActivity {
//
//    private RecyclerView rvNotes;
//    private NoteAdapter adapter;
//    private NoteHelper noteHelper;
//
//    private TextView noData;
//    private FloatingActionButton fabAdd;
//    private final int REQUEST_ADD = 100;
//    private final int REQUEST_UPDATE = 200;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Student List");
//        }
//
//        rvNotes = findViewById(R.id.rv_notes);
//        fabAdd = findViewById(R.id.fab_add);
//        noData = findViewById(R.id.noData);
//
//        rvNotes.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new NoteAdapter(this);
//        rvNotes.setAdapter(adapter);
//        noteHelper = NoteHelper.getInstance(getApplicationContext());
//        noteHelper.open();
//
//        fabAdd.setOnClickListener(v -> {
//            Intent intent = new Intent(this, FormActivity.class);
//            startActivityForResult(intent, REQUEST_ADD);
//        });
//
//        loadNotes();
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (noteHelper != null) {
//            noteHelper.close();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_ADD && resultCode == FormActivity.RESULT_ADD) {
//            showToast("Note added successfully");
//            loadNotes();
//        } else if (requestCode == REQUEST_UPDATE) {
//            if (resultCode == FormActivity.RESULT_UPDATE) {
//                showToast("Note updated successfully");
//                loadNotes();
//            } else if (resultCode == FormActivity.RESULT_DELETE) {
//                showToast("Note deleted successfully");
//                loadNotes();
//            }
//        }
//    }
//
//    private static class LoadNotesAsync {
//        private final WeakReference<Context> weakContext;
//        private final WeakReference<LoadNotesCallback> weakCallback;
//
//        private LoadNotesAsync(Context context, LoadNotesCallback callback) {
//            weakContext = new WeakReference<>(context);
//            weakCallback = new WeakReference<>(callback);
//        }
//
//        void execute() {
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//            Handler handler = new Handler(Looper.getMainLooper());
//
//            executor.execute(() -> {
//                Context context = weakContext.get();
//                if (context != null) {
//                    NoteHelper noteHelper = NoteHelper.getInstance(context);
//                    noteHelper.open();
//
//                    Cursor notesCursor = noteHelper.queryAll();
//                    ArrayList<Note> notes = MappingHelper.mapCursorToArrayList(notesCursor);
//                    notesCursor.close();
//
//                    handler.post(() -> {
//                        LoadNotesCallback callback = weakCallback.get();
//                        if (callback != null) {
//                            callback.postExecute(notes);
//                        }
//                    });
//                }
//            });
//        }
//    }
//
//    private void loadNotes() {
//        new LoadNotesAsync(this, notes -> {
//            if (notes.size() > 0) {
//                adapter.setNotes(notes);
//                noData.setVisibility(View.GONE);
//            } else {
//                adapter.setNotes(new ArrayList<>());
//                noData.setVisibility(View.VISIBLE);
//                showToast("No data available");
//            }
//        }).execute();
//    }
//
//    interface LoadNotesCallback {
//        void postExecute(ArrayList<Note> notes);
//    }
//}

package com.example.tp8;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private NoteAdapter adapter;
    private NoteHelper noteHelper;

    private TextView noData;
    private TextView tvNotesCount;
    private EditText etSearch;
    private FloatingActionButton fabAdd;

    private final int REQUEST_ADD = 100;
    private final int REQUEST_UPDATE = 200;

    private ArrayList<Note> allNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Student List");
        }

        rvNotes = findViewById(R.id.rv_notes);
        fabAdd = findViewById(R.id.fab_add);
        noData = findViewById(R.id.noData);
        tvNotesCount = findViewById(R.id.tv_notes_count);
        etSearch = findViewById(R.id.et_search);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(this);
        rvNotes.setAdapter(adapter);

        noteHelper = NoteHelper.getInstance(getApplicationContext());
        noteHelper.open();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        loadNotes();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noteHelper != null) {
            noteHelper.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD && resultCode == FormActivity.RESULT_ADD) {
            showToast("Note added successfully");
            loadNotes();
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

    private void loadNotes() {
        new LoadNotesAsync(this, notes -> {
            allNotes = notes;
            adapter.setNotes(notes);
            tvNotesCount.setText(notes.size() + " notes available");

            if (notes.size() > 0) {
                noData.setVisibility(View.GONE);
            } else {
                noData.setVisibility(View.VISIBLE);
                showToast("No data available");
            }
        }).execute();
    }

    private void filterNotes(String query) {
        ArrayList<Note> filtered = new ArrayList<>();
        for (Note note : allNotes) {
            if (note.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    note.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(note);
            }
        }

        adapter.setNotes(filtered);
        noData.setVisibility(filtered.isEmpty() ? View.VISIBLE : View.GONE);
        tvNotesCount.setText(filtered.size() + " notes available");
    }

    interface LoadNotesCallback {
        void postExecute(ArrayList<Note> notes);
    }
}

