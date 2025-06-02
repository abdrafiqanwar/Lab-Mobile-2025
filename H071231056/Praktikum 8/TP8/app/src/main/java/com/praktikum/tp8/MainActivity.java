package com.praktikum.tp8;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapter.OnNoteActionListener {
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private DatabaseHelper databaseHelper;
    private List<Note> notesList;
    private EditText searchEditText;
    private ImageView clearSearchIcon;
    private TextView noDataTextView;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupSearch();
        loadNotes();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            startActivity(intent);
        });

        clearSearchIcon.setOnClickListener(v -> {
            searchEditText.setText("");
            clearSearchIcon.setVisibility(View.GONE);
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        searchEditText = findViewById(R.id.et_search);
        clearSearchIcon = findViewById(R.id.iv_clear_search);
        noDataTextView = findViewById(R.id.tv_no_data);
        fabAdd = findViewById(R.id.fab_add);

        databaseHelper = new DatabaseHelper(this);
        notesList = new ArrayList<>();
    }

    private void setupRecyclerView() {
        notesAdapter = new NotesAdapter(this, notesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notesAdapter);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (query.isEmpty()) {
                    clearSearchIcon.setVisibility(View.GONE);
                    loadNotes();
                } else {
                    clearSearchIcon.setVisibility(View.VISIBLE);
                    searchNotes(query);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadNotes() {
        notesList = databaseHelper.getAllNotes();
        notesAdapter.updateNotes(notesList);
        updateNoDataVisibility();
    }

    private void searchNotes(String query) {
        notesList = databaseHelper.searchNotes(query);
        notesAdapter.updateNotes(notesList);
        updateNoDataVisibility();
    }

    private void updateNoDataVisibility() {
        if (notesList.isEmpty()) {
            noDataTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noDataTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (searchEditText.getText().toString().trim().isEmpty()) {
            loadNotes();
        } else {
            searchNotes(searchEditText.getText().toString().trim());
        }
    }

    @Override
    public void onNoteDeleted() {
        updateNoDataVisibility();
    }
}