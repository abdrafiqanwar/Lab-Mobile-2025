package com.example.tp7_h071231092.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp7_h071231092.R;
import com.example.tp7_h071231092.data.DatabaseContract;
import com.example.tp7_h071231092.data.Notes;
import com.example.tp7_h071231092.util.NotesHelper;

public class FormActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE = "extra_note";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_UPDATE = 200;

    private NotesHelper notesHelper;
    private Notes note;
    private EditText etJudul, etDeskripsi;
    private Button btnSave, btnDelete;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable "Up" button
        }

        etJudul = findViewById(R.id.et_judul);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        btnDelete = findViewById(R.id.btn_delete);
        btnSave = findViewById(R.id.btn_save);

        notesHelper = NotesHelper.getInstance(getApplicationContext());
        notesHelper.open();

        note = getIntent().getParcelableExtra(EXTRA_NOTE);

        if (note != null) {
            isEdit = true;
        } else {
            note = new Notes();
        }

        String actionBarTitle;
        String buttonTitle;

        if (isEdit) {
            actionBarTitle = "Edit Note";
            buttonTitle = "Update";

            if (note != null) {
                etJudul.setText(note.getJudul());
                etDeskripsi.setText(note.getDeskripsi());
            }
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            actionBarTitle = "Add Note";
            buttonTitle = "Save";
        }

        btnSave.setText(buttonTitle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSave.setOnClickListener(view -> saveNote());
        btnDelete.setOnClickListener(view -> deleteNote());
    }

    private void saveNote() {
        String judul = etJudul.getText().toString().trim();
        String deskripsi = etDeskripsi.getText().toString().trim();

        if (judul.isEmpty()) {
            etJudul.setError("Please fill this field");
            return;
        }

        if (deskripsi.isEmpty()) {
            etDeskripsi.setError("Please fill this field");
            return;
        }

        note.setJudul(judul);
        note.setDeskripsi(deskripsi);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NOTE, note);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.NotesColumn.JUDUL, judul);
        values.put(DatabaseContract.NotesColumn.DESKRIPSI, deskripsi);

        if (isEdit) {
            // Format waktu saat update
            String updatedAt = "Update at " + new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                    .format(new java.util.Date(System.currentTimeMillis()));
            values.put(DatabaseContract.NotesColumn.CREATED_AT, updatedAt);

            long result = notesHelper.update(String.valueOf(note.getId()), values);
            if (result > 0) {
                setResult(RESULT_UPDATE, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Format waktu saat tambah data
            String createdAt = "Created at " + new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                    .format(new java.util.Date(System.currentTimeMillis()));
            values.put(DatabaseContract.NotesColumn.CREATED_AT, createdAt);

            long result = notesHelper.insert(values);
            if (result > 0) {
                note.setId((int) result);
                setResult(RESULT_ADD, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteNote() {
        if (note != null && note.getId() > 0) {
            long result = notesHelper.deleteById(String.valueOf(note.getId()));
            if (result > 0) {
                setResult(RESULT_DELETE);
                finish();
            } else {
                Toast.makeText(this, "Failed to delete data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid note data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notesHelper != null) {
            notesHelper.close();
        }
    }
}