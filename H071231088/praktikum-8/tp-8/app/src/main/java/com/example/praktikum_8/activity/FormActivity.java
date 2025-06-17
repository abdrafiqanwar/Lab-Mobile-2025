package com.example.praktikum_8.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praktikum_8.R;
import com.example.praktikum_8.database.DatabaseContract;
import com.example.praktikum_8.database.NoteHelper;
import com.example.praktikum_8.note.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends Activity {
    public static final String EXTRA_NOTE = "extra_note";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_UPDATE = 200;
    private NoteHelper noteHelper;
    private Note note;
    private EditText etJudul, etDeskripsi;
    private boolean isEdit = false;
    private String initialJudul = "";
    private String initialDeskripsi = "";
    private ImageView btnDelete;
    private TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.praktikum_8.R.layout.activity_form);

        ImageView btnBack = findViewById(R.id.btn_back);
        etJudul = findViewById(R.id.et_judul);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        Button btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);

        noteHelper = NoteHelper.getInstance(getApplicationContext());
        noteHelper.open();

        note = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (note != null) {
            isEdit = true;
            initialJudul = note.getJudul();
            initialDeskripsi = note.getDeskripsi();
            etJudul.setText(initialJudul);
            etDeskripsi.setText(initialDeskripsi);
        } else {
            note = new Note();
        }

        String toolbarTitle = isEdit ? "Edit Catatan" : "Tambah Catatan";
        String buttonTitle = isEdit ? "Perbarui" : "Simpan";
        tvToolbarTitle.setText(toolbarTitle);
        btnSave.setText(buttonTitle);
        btnDelete.setVisibility(isEdit ? View.VISIBLE : View.GONE);

        btnBack.setOnClickListener(v -> onBackPressed());
        btnSave.setOnClickListener(view -> saveNote());
        btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Anda yakin ingin menghapus catatan ini?")
                    .setPositiveButton("Hapus", (dialog, which) -> deleteNote())
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }

    private void saveNote() {
        String judul = etJudul.getText().toString().trim();
        String deskripsi = etDeskripsi.getText().toString().trim();

        if (judul.isEmpty()) {
            etJudul.setError("Judul tidak boleh kosong");
            return;
        }

        if (deskripsi.isEmpty()) {
            etDeskripsi.setError("Deskripsi tidak boleh kosong");
            return;
        }

        note.setJudul(judul);
        note.setDeskripsi(deskripsi);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_NOTE, note);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.NoteColumn.JUDUL, judul);
        values.put(DatabaseContract.NoteColumn.DESKRIPSI, deskripsi);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

        long result;
        if (isEdit) {
            values.put(DatabaseContract.NoteColumn.UPDATED_AT, currentDate);
            note.setUpdatedAt(currentDate);

            result = noteHelper.update(String.valueOf(note.getId()), values);
            if (result > 0) {
                setResult(RESULT_UPDATE, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Gagal memperbarui catatan.", Toast.LENGTH_SHORT).show();
            }
        } else {
            values.put(DatabaseContract.NoteColumn.CREATED_AT, currentDate);
            note.setCreatedAt(currentDate);

            result = noteHelper.insert(values);
            if (result > 0) {
                note.setId((int) result);
                setResult(RESULT_ADD, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Gagal menambahkan catatan.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Gagal menghapus catatan.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Data catatan tidak valid.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFormChanged() {
        String currentJudul = etJudul.getText().toString().trim();
        String currentDeskripsi = etDeskripsi.getText().toString().trim();

        if (isEdit) {
            return !currentJudul.equals(initialJudul) || !currentDeskripsi.equals(initialDeskripsi);
        } else {
            return !currentJudul.isEmpty() || !currentDeskripsi.isEmpty();
        }
    }

    @Override
    public void onBackPressed() {
        if (isFormChanged()) {
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Ada perubahan yang belum disimpan. Yakin ingin keluar?")
                    .setPositiveButton("Ya", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Batal", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noteHelper != null) {
            noteHelper.close();
        }
    }
}