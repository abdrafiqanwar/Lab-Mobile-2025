package com.projeku.tuprak8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.projeku.tuprak8.Note;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener {

    private RecyclerView recyclerViewNotes;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;
    private DatabaseHelper dbHelper;
    private FloatingActionButton fabAddNote;
    private EditText editTextSearch;
    private ImageButton buttonClearSearch;
    private TextView textViewNoData;
    private Toolbar toolbarMain;

    private ActivityResultLauncher<Intent> addEditNoteLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarMain = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.notes_toolbar_title));
        }

        dbHelper = new DatabaseHelper(this);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        fabAddNote = findViewById(R.id.fab_add_note);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonClearSearch = findViewById(R.id.buttonClearSearch);
        textViewNoData = findViewById(R.id.textViewNoData);

        // Initialize note list and adapter
        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, noteList, this);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(noteAdapter);

        loadNotes();

        // Set up result launcher for add/edit note activity
        addEditNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadNotes();
                        preventKeyboardFromShowing();
                    }
                }
        );

        // Set up fab click listener
        fabAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            addEditNoteLauncher.launch(intent);
        });

        // Set up search functionality
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchNotes(s.toString());
                if (s.length() > 0) {
                    buttonClearSearch.setVisibility(View.VISIBLE);
                } else {
                    buttonClearSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set up clear button functionality
        buttonClearSearch.setOnClickListener(v -> {
            editTextSearch.setText("");
        });

        // Prevent keyboard from showing on startup
        preventKeyboardFromShowing();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preventKeyboardFromShowing();
    }

    private void preventKeyboardFromShowing() {
        if (editTextSearch != null) {
            editTextSearch.clearFocus();
        }

        if (toolbarMain != null) {
            toolbarMain.requestFocus();
        } else {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                decorView.requestFocus();
            }
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = getCurrentFocus();
        if (imm != null) {
            if (currentFocusedView != null) {
                imm.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
            } else {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }

    private void loadNotes() {
        String currentSearchQuery = editTextSearch.getText().toString().trim();

        if (currentSearchQuery.isEmpty()) {
            noteList.clear();
            noteList.addAll(dbHelper.getAllNotes());
            noteAdapter.setNotes(noteList);
            checkIfNoData(null);
        } else {
            searchNotes(currentSearchQuery);
        }
    }

    private void searchNotes(String query) {
        noteList.clear();
        if (query.isEmpty()) {
            noteList.addAll(dbHelper.getAllNotes());
        } else {
            noteList.addAll(dbHelper.searchNotesByTitle(query));
        }
        noteAdapter.setNotes(noteList);
        checkIfNoData(query);
    }

    private void checkIfNoData(String searchQuery) {
        if (noteList.isEmpty()) {
            recyclerViewNotes.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);

            if (searchQuery != null && !searchQuery.isEmpty()) {
                textViewNoData.setText(String.format(getString(R.string.no_search_results), searchQuery));
            } else {
                textViewNoData.setText(getString(R.string.no_data_available));
            }
        } else {
            textViewNoData.setVisibility(View.GONE);
            recyclerViewNotes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(Note note) {
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        intent.putExtra("note_id", note.getId());
        addEditNoteLauncher.launch(intent);
    }
}