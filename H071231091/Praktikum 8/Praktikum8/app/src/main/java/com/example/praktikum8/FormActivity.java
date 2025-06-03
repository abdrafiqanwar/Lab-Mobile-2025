package com.example.praktikum8;

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

import com.example.database.DatabaseContract;
import com.example.database.StudentHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT = "extra_student";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_UPDATE = 200;

    private StudentHelper studentHelper;  // Bantu akses database
    private Student student;
    private EditText etName, etNim;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etName = findViewById(R.id.et_name);
        etNim = findViewById(R.id.et_nim);
        Button btnSave = findViewById(R.id.btn_save);
        Button btnDelete = findViewById(R.id.btn_delete);


        studentHelper = StudentHelper.getInstance(getApplicationContext());
        studentHelper.open();


        student = getIntent().getParcelableExtra(EXTRA_STUDENT);
        if (student != null) {
            isEdit = true;  // Mode edit
            etName.setText(student.getName());
            etNim.setText(student.getNim());
        } else {
            // Mode tambah data baru
            student = new Student();
        }

        String actionBarTitle = isEdit ? "Edit Student" : "Add Student";
        String buttonTitle = isEdit ? "Update" : "Save";

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSave.setText(buttonTitle);
        btnDelete.setVisibility(isEdit ? View.VISIBLE : View.GONE);

        btnSave.setOnClickListener(v -> saveStudent());
        btnDelete.setOnClickListener(v -> deleteStudent());
    }


    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private void saveStudent() {
        String name = etName.getText().toString().trim();
        String nim = etNim.getText().toString().trim();

        // Cek apakah nama dan NIM sudah diisi
        if (name.isEmpty()) {
            etName.setError("Please fill this field");
            return;
        }
        if (nim.isEmpty()) {
            etNim.setError("Please fill this field");
            return;
        }

        // Siapkan data yang akan disimpan ke database
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.StudentColumns.NAME, name);
        values.put(DatabaseContract.StudentColumns.NIM, nim);

        Intent intent = new Intent();
        student.setName(name);
        student.setNim(nim);

        if (isEdit) {
            // Jika edit, tambahkan waktu updatex
            String timestamp = getCurrentTimestamp();
            values.put(DatabaseContract.StudentColumns.UPDATED_AT, timestamp);
            student.setUpdatedAt(timestamp);

            // Update data di database
            long result = studentHelper.update(String.valueOf(student.getId()), values);
            if (result > 0) {
                // Berhasil update, kirim hasil
                intent.putExtra(EXTRA_STUDENT, student);
                setResult(RESULT_UPDATE, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }
        } else {
            String timestamp = getCurrentTimestamp();
            values.put(DatabaseContract.StudentColumns.CREATED_AT, timestamp);
            student.setCreatedAt(timestamp);

            // Masukkan data baru ke database
            long result = studentHelper.insert(values);
            if (result > 0) {
                // Berhasil tambah, set id baru dan kirim hasil
                student.setId((int) result);
                intent.putExtra(EXTRA_STUDENT, student);
                setResult(RESULT_ADD, intent);
                finish();
            } else {
                // Gagal tambah, tampilkan pesan error
                Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteStudent() {
        long result = studentHelper.deleteById(String.valueOf(student.getId()));
        if (result > 0) {
            setResult(RESULT_DELETE);
            finish();
        } else {
            Toast.makeText(this, "Failed to delete data", Toast.LENGTH_SHORT).show();
        }
    }

    // Tombol back di action bar ditekan, tutup activity ini
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Tutup koneksi database saat activity dihancurkan
    @Override
    protected void onDestroy() {
        super.onDestroy();
        studentHelper.close();
    }
}
