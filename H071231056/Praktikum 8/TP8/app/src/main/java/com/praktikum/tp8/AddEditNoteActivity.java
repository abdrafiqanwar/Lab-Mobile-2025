package com.praktikum.tp8;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddEditNoteActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button submitButton;
    private DatabaseHelper databaseHelper;
    private int noteId = -1;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        initViews();
        setupToolbar();
        checkEditMode();

        submitButton.setOnClickListener(v -> saveNote());
    }

    private void initViews() {
        titleEditText = findViewById(R.id.et_title);
        descriptionEditText = findViewById(R.id.et_description);
        submitButton = findViewById(R.id.btn_submit);
        databaseHelper = new DatabaseHelper(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void checkEditMode() {
        if (getIntent().hasExtra("note_id")) {
            isEditMode = true;
            noteId = getIntent().getIntExtra("note_id", -1);
            String title = getIntent().getStringExtra("note_title");
            String description = getIntent().getStringExtra("note_description");

            titleEditText.setText(title);
            descriptionEditText.setText(description);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Edit");
            }
            submitButton.setText("Ubah");
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Tambah");
            }
            submitButton.setText("Submit");
        }
    }


    private void saveNote() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty()) {
            titleEditText.setError("Title is required");
            titleEditText.requestFocus();
            return;
        }

        if (isEditMode) {
            // Show confirmation dialog for update
            showUpdateConfirmationDialog(title, description);
        } else {
            // Add new note
            long result = databaseHelper.addNote(title, description);
            if (result != -1) {
                Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add note", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showUpdateConfirmationDialog(String title, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update");
        builder.setMessage("Apakah anda ingin mengupdate note ini?");
        builder.setPositiveButton("YA", (dialog, which) -> {
            int result = databaseHelper.updateNote(noteId, title, description);
            if (result > 0) {
                Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEditMode) {
            getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            showCancelConfirmationDialog();
            return true;
        } else if (itemId == R.id.action_delete) {
                showDeleteConfirmationDialog();
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        }

    private void showCancelConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Batal");
        builder.setMessage("Apakah anda ingin membatalkan form?");
        builder.setPositiveButton("YA", (dialog, which) -> finish());
        builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus");
        builder.setMessage("Apakah anda yakin ingin menghapus note ini?");
        builder.setPositiveButton("YA", (dialog, which) -> {
            databaseHelper.deleteNote(noteId);
            Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
        builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}