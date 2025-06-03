//package com.example.tp8;
//
//import android.content.ContentValues;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class FormActivity extends AppCompatActivity {
//
//    public static final String EXTRA_NOTE = "extra_note";
//    public static final int RESULT_ADD = 101;
//    public static final int RESULT_UPDATE = 201;
//    public static final int RESULT_DELETE = 301;
//    public static final int REQUEST_UPDATE = 200;
//
//    private NoteHelper noteHelper;
//    private Note note;
//    private EditText etTitle, etDesc;
//    private boolean isEdit = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_form);
//
//        etTitle = findViewById(R.id.et_title);
//        etDesc = findViewById(R.id.et_desc);
//        Button btnSave = findViewById(R.id.btn_save);
//        Button btnDelete = findViewById(R.id.btn_delete);
//
//        noteHelper = NoteHelper.getInstance(getApplicationContext());
//        noteHelper.open();
//
//        note = getIntent().getParcelableExtra(EXTRA_NOTE);
//
//        if (note != null) {
//            isEdit = true;
//        } else {
//            note = new Note();
//        }
//
//        String actionBarTitle;
//        String buttonTitle;
//
//        if (isEdit) {
//            actionBarTitle = "Edit Note";
//            buttonTitle = "Update";
//
//            etTitle.setText(note.getTitle());
//            etDesc.setText(note.getDescription());
//
//            btnDelete.setVisibility(View.VISIBLE);
//        } else {
//            actionBarTitle = "Add Note";
//            buttonTitle = "Save";
//            btnDelete.setVisibility(View.GONE);
//        }
//
//        btnSave.setText(buttonTitle);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(actionBarTitle);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        btnSave.setOnClickListener(view -> saveNote());
//        btnDelete.setOnClickListener(view -> deleteNote());
//    }
//
//    private void saveNote() {
//        String title = etTitle.getText().toString().trim();
//        String desc = etDesc.getText().toString().trim();
//
//        if (title.isEmpty()) {
//            etTitle.setError("Please fill this field");
//            return;
//        }
//
//        note.setTitle(title);
//        note.setDescription(desc);
//
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_NOTE, note);
//
//        ContentValues values = new ContentValues();
//        values.put(DatabaseContract.NoteColumns.TITLE, title);
//        values.put(DatabaseContract.NoteColumns.DESC, desc);
//
//        if (isEdit) {
//            String updatedAt = getCurrentDate();
//            note.setDate(updatedAt);
//            values.put(DatabaseContract.NoteColumns.DATE, updatedAt);
//
//            long result = noteHelper.update(String.valueOf(note.getId()), values);
//            if (result > 0) {
//                setResult(RESULT_UPDATE, intent);
//                finish();
//            } else {
//                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            String date = getCurrentDate();
//            note.setDate(date);
//            values.put(DatabaseContract.NoteColumns.DATE, date);
//
//            long result = noteHelper.insert(values);
//            if (result > 0) {
//                note.setId((int) result);
//                setResult(RESULT_ADD, intent);
//                finish();
//            } else {
//                Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void deleteNote() {
//        if (note != null && note.getId() > 0) {
//            long result = noteHelper.deleteById(String.valueOf(note.getId()));
//            if (result > 0) {
//                setResult(RESULT_DELETE);
//                finish();
//            } else {
//                Toast.makeText(this, "Failed to delete data", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "Invalid note data", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private String getCurrentDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
//        return sdf.format(new Date());
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (noteHelper != null) {
//            noteHelper.close();
//        }
//    }
//}


package com.example.tp8;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE = "extra_note";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_UPDATE = 200;

    private NoteHelper noteHelper;
    private Note note;
    private EditText etTitle, etDesc;
    private boolean isEdit = false;

    private String originalTitle = "";
    private String originalDesc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etTitle = findViewById(R.id.et_title);
        etDesc = findViewById(R.id.et_desc);
        Button btnSave = findViewById(R.id.btn_save);
        Button btnDelete = findViewById(R.id.btn_delete);

        noteHelper = NoteHelper.getInstance(getApplicationContext());
        noteHelper.open();

        note = getIntent().getParcelableExtra(EXTRA_NOTE);

        if (note != null) {
            isEdit = true;
        } else {
            note = new Note();
        }

        String actionBarTitle;
        String buttonTitle;

        if (isEdit) {
            actionBarTitle = "Edit Note";
            buttonTitle = "Update";

            etTitle.setText(note.getTitle());
            etDesc.setText(note.getDescription());

            originalTitle = note.getTitle();
            originalDesc = note.getDescription();

            btnDelete.setVisibility(View.VISIBLE);
        } else {
            actionBarTitle = "Add Note";
            buttonTitle = "Save";
            btnDelete.setVisibility(View.GONE);
        }

        btnSave.setText(buttonTitle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSave.setOnClickListener(view -> saveNote());

        btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> deleteNote())
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Please fill this field");
            return;
        }

        note.setTitle(title);
        note.setDescription(desc);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NOTE, note);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.NoteColumns.TITLE, title);
        values.put(DatabaseContract.NoteColumns.DESC, desc);

        if (isEdit) {
            String updatedAt = getCurrentDate();
            note.setDate(updatedAt);
            values.put(DatabaseContract.NoteColumns.DATE, updatedAt);

            long result = noteHelper.update(String.valueOf(note.getId()), values);
            if (result > 0) {
                setResult(RESULT_UPDATE, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }
        } else {
            String date = getCurrentDate();
            note.setDate(date);
            values.put(DatabaseContract.NoteColumns.DATE, date);

            long result = noteHelper.insert(values);
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
            long result = noteHelper.deleteById(String.valueOf(note.getId()));
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

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private boolean isChanged() {
        String currentTitle = etTitle.getText().toString().trim();
        String currentDesc = etDesc.getText().toString().trim();
        return !currentTitle.equals(originalTitle) || !currentDesc.equals(originalDesc);
    }

    @Override
    public void onBackPressed() {
        if (isEdit && isChanged()) {
            new AlertDialog.Builder(this)
                    .setTitle("Discard Changes?")
                    .setMessage("You have unsaved changes. Are you sure you want to discard them?")
                    .setPositiveButton("Yes", (dialog, which) -> finish())
                    .setNegativeButton("Cancel", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isEdit && isChanged()) {
                new AlertDialog.Builder(this)
                        .setTitle("Discard Changes?")
                        .setMessage("You have unsaved changes. Are you sure you want to discard them?")
                        .setPositiveButton("Yes", (dialog, which) -> finish())
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noteHelper != null) {
            noteHelper.close();
        }
    }
}

