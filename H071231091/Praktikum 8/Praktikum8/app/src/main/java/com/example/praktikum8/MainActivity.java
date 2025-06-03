// Package utama dari aplikasi
package com.example.praktikum8;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.StudentHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {
    private RecyclerView rvStudents;
    private StudentAdapter adapter;
    private StudentHelper studentHelper;
    private TextView noData;
    private EditText etSearch;

    private static final int REQUEST_ADD = 100;     // Kode request untuk intent tambah mahasiswa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi view
        rvStudents = findViewById(R.id.rv_students);
        noData = findViewById(R.id.noData); 
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        etSearch = findViewById(R.id.et_search);
        Button btnSearch = findViewById(R.id.btn_search);

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this);
        rvStudents.setAdapter(adapter);

        // Buka koneksi ke database
        studentHelper = StudentHelper.getInstance(this);
        studentHelper.open();

        // Tombol
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        btnSearch.setOnClickListener(v -> performSearch());

        // Deteksi saat tombol "search" di keyboard ditekan
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                performSearch();
                return true;
            }
            return false;
        });

        // Deteksi perubahan teks untuk fitur pencarian langsung (live search)
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    performSearch(); // Jika ada teks, lakukan pencarian
                } else {
                    loadStudents();
                }
            }
        });


        loadStudents();
    }

    /**
     * Fungsi untuk melakukan pencarian mahasiswa berdasarkan teks input
     */
    private void performSearch() {
        String query = etSearch.getText().toString().trim();
        if (query.isEmpty()) {
            loadStudents(); // Tampilkan semua data jika input kosong
            return;
        }

        new SearchStudentsAsync(this, students -> {
            if (!students.isEmpty()) {
                adapter.setStudents(students);
                noData.setVisibility(View.GONE);
            } else {
                adapter.setStudents(new ArrayList<>());
                noData.setVisibility(View.VISIBLE); // Tampilkan teks "No Data"
            }
        }).execute(query);
    }

    private void loadStudents() {
        new LoadStudentsAsync(this, students -> {
            if (!students.isEmpty()) {
                adapter.setStudents(students);
                noData.setVisibility(View.GONE);
            } else {
                adapter.setStudents(new ArrayList<>());
                noData.setVisibility(View.VISIBLE);
            }
        }).execute();
    }

    /**
     * Fungsi yang dijalankan setelah kembali dari activity lain (FormActivity)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Jika data diubah, reload data dari database
        if (resultCode == FormActivity.RESULT_ADD ||
                resultCode == FormActivity.RESULT_UPDATE ||
                resultCode == FormActivity.RESULT_DELETE) {
            loadStudents();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        studentHelper.close();
    }

    private static class LoadStudentsAsync {

        // Gunakan WeakReference agar tidak menyebabkan memory leak (kebocoran memori)
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadStudentsCallback> weakCallback;

        private LoadStudentsAsync(Context context, LoadStudentsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        // Fungsi untuk menjalankan proses load data
        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();

            // Handler akan mengirim hasilnya ke thread utama (UI thread)
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                // Ambil context dari WeakReference
                Context context = weakContext.get();

                // Pastikan context tidak null
                if (context != null) {
                    // Buka koneksi
                    StudentHelper studentHelper = StudentHelper.getInstance(context);
                    studentHelper.open();
                    Cursor cursor = studentHelper.queryAll();
                    // Konversi hasil Cursor menjadi ArrayList<Student>
                    ArrayList<Student> students = MappingHelper.mapCursorToArrayList(cursor);
                    cursor.close();

                    // Kirim hasil ke UI thread agar bisa ditampilkan ke RecyclerView
                    handler.post(() -> {
                        LoadStudentsCallback callback = weakCallback.get();

                        if (callback != null) {
                            callback.postExecute(students);
                        }
                    });
                }
            });
        }
    }
    private static class SearchStudentsAsync {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadStudentsCallback> weakCallback;

        private SearchStudentsAsync(Context context, LoadStudentsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        // Fungsi untuk mengeksekusi proses pencarian data mahasiswa
        void execute(String query) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                // Ambil context dari WeakReference
                Context context = weakContext.get();

                if (context != null) {
                    StudentHelper studentHelper = StudentHelper.getInstance(context);
                    studentHelper.open();
                    Cursor cursor = studentHelper.searchByTitle(query);//search
                    // Ubah hasil cursor ke dalam bentuk ArrayList<Student>
                    ArrayList<Student> students = MappingHelper.mapCursorToArrayList(cursor);
                    cursor.close();

                    // Kirim hasil pencarian ke UI thread
                    handler.post(() -> {
                        LoadStudentsCallback callback = weakCallback.get();

                        // Jika callback tidak null, kirim data hasil pencarian ke postExecute()
                        if (callback != null) {
                            callback.postExecute(students);
                        }
                    });
                }
            });
        }
    }


    /**
     * Interface untuk callback saat data mahasiswa berhasil dimuat atau ditemukan
     */
    interface LoadStudentsCallback {
        void postExecute(ArrayList<Student> students);
    }
}
