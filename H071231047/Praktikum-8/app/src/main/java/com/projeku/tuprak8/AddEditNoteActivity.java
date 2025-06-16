package com.projeku.tuprak8; // Sesuaikan dengan package Anda

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast; // Toast untuk error masih bisa digunakan jika perlu

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddEditNoteActivity extends AppCompatActivity {

    private TextInputEditText editTextNoteTitle;
    private TextInputEditText editTextNoteContent;
    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutContent;
    private Button buttonSaveNote;
    private Toolbar toolbarAddEditNote;

    private DatabaseHelper dbHelper;
    private Note currentNote;
    private boolean isEditMode = false;
    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        dbHelper = new DatabaseHelper(this);

        toolbarAddEditNote = findViewById(R.id.toolbar_add_edit_note);
        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);
        textInputLayoutTitle = findViewById(R.id.text_input_layout_title);
        textInputLayoutContent = findViewById(R.id.text_input_layout_content);
        buttonSaveNote = findViewById(R.id.button_save_note);

        setSupportActionBar(toolbarAddEditNote);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().hasExtra("note_id")) {
            noteId = getIntent().getIntExtra("note_id", -1);
            if (noteId != -1) {
                isEditMode = true;
                currentNote = dbHelper.getNoteById(noteId);
                if (currentNote != null) {
                    populateNoteData();
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getString(R.string.edit_note_toolbar_title));
                    }
                    buttonSaveNote.setText(getString(R.string.button_update_text));
                } else {
                    Toast.makeText(this, "Error: Note not found.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.add_note_toolbar_title));
            }
            buttonSaveNote.setText(getString(R.string.button_submit_text));
        }

        buttonSaveNote.setOnClickListener(v -> saveOrUpdateNote());
    }

    private void populateNoteData() {
        if (currentNote != null) {
            editTextNoteTitle.setText(currentNote.getTitle());
            editTextNoteContent.setText(currentNote.getContent());
        }
    }

    private boolean validateInput() {
        String title = editTextNoteTitle.getText().toString().trim();
        String content = editTextNoteContent.getText().toString().trim();

        if (title.isEmpty()) {
            textInputLayoutTitle.setError(getString(R.string.save_note_error_title_empty));
            return false;
        } else {
            textInputLayoutTitle.setError(null);
        }

        if (content.isEmpty()) {
            textInputLayoutContent.setError(getString(R.string.save_note_error_content_empty));
            return false;
        } else {
            textInputLayoutContent.setError(null);
        }
        return true;
    }

    private void saveOrUpdateNote() {
        if (!validateInput()) {
            return;
        }

        String title = editTextNoteTitle.getText().toString().trim();
        String content = editTextNoteContent.getText().toString().trim();
        // String toastMessage; // Tidak diperlukan lagi untuk pesan sukses

        if (isEditMode && currentNote != null) {
            currentNote.setTitle(title);
            currentNote.setContent(content);
            int rowsAffected = dbHelper.updateNote(currentNote);
            // if (rowsAffected > 0) {
            //     toastMessage = getString(R.string.note_updated_successfully);
            // } else {
            //     toastMessage = getString(R.string.error_updating_note); // Biarkan pesan error jika diperlukan
            // }
        } else {
            Note newNote = new Note(title, content, "", "");
            long newRowId = dbHelper.addNote(newNote);
            // if (newRowId != -1) {
            //     toastMessage = getString(R.string.note_saved_successfully);
            // } else {
            //     toastMessage = getString(R.string.error_saving_note); // Biarkan pesan error jika diperlukan
            // }
        }

        Intent resultIntent = new Intent();
        // resultIntent.putExtra("toast_message", toastMessage); // BARIS INI DIHAPUS/DIKOMENTARI
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEditMode) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_add_edit_note, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete_note) {
            showDeleteConfirmationDialog();
            return true;
        } else if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_note_dialog_title))
                .setMessage(getString(R.string.delete_note_dialog_message))
                .setPositiveButton(getString(R.string.dialog_button_yes), (dialog, which) -> deleteNote())
                .setNegativeButton(getString(R.string.dialog_button_no), null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteNote() {
        if (currentNote != null) {
            dbHelper.deleteNote(currentNote.getId());
            // String toastMessage = getString(R.string.note_deleted_successfully); // Tidak diperlukan lagi

            Intent resultIntent = new Intent();
            // resultIntent.putExtra("toast_message", toastMessage); // BARIS INI DIHAPUS/DIKOMENTARI
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.error_deleting_note), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
