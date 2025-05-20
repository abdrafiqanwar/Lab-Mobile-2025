package com.projeku.tuprak4.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.projeku.tuprak4.book.Book;
import com.projeku.tuprak4.book.DataBook;
import com.projeku.tuprak4.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddFragment extends Fragment {
    // Deklarasi komponen UI
    private ImageView bookImage;
    Spinner spinnerGenre;
    private EditText editTitle, editAuthor, editGenre, editYear, editBlurb;
    private Button btnSelectImage, btnAddBook;
    private Uri selectedImageUri = null; // Menyimpan URI gambar yang dipilih

    // Launcher untuk memilih gambar dari galeri
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Jika hasil berhasil dan data tidak null
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData(); // Ambil URI gambar
                    Glide.with(this).load(selectedImageUri).into(bookImage); // Tampilkan gambar ke ImageView
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout fragment_add ke dalam view
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Inisialisasi semua view berdasarkan ID-nya di XML
        bookImage = view.findViewById(R.id.book_image);
        editTitle = view.findViewById(R.id.edit_title);
        editAuthor = view.findViewById(R.id.edit_author);
        spinnerGenre = view.findViewById(R.id.spinner_genre);
        editYear = view.findViewById(R.id.edit_year);
        editBlurb = view.findViewById(R.id.edit_blurb);
        btnSelectImage = view.findViewById(R.id.btn_select_image);
        btnAddBook = view.findViewById(R.id.btn_add_book);

        // Isi data spinner genre
        populateGenreSpinner();

        // Event klik tombol pilih gambar
        btnSelectImage.setOnClickListener(v -> openImagePicker());

        // Event klik tombol tambah buku
        btnAddBook.setOnClickListener(v -> saveBook());

        return view;
    }

    // Fungsi untuk mengisi spinner dengan genre unik dari daftar buku
    private void populateGenreSpinner() {
        Set<String> genres = new HashSet<>();
        for (Book book : DataBook.bookList) {
            genres.add(book.getGenre()); // Tambahkan genre unik ke dalam set
        }

        // Buat adapter dari set genre ke spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                new ArrayList<>(genres)
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter); // Set adapter ke spinner
    }

    // Fungsi untuk membuka galeri guna memilih gambar
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent); // Jalankan intent dengan launcher
    }

    // Fungsi untuk menyimpan buku baru
    private void saveBook() {
        // Validasi semua field wajib diisi dan gambar dipilih
        if (editTitle.getText().toString().isEmpty() ||
                editAuthor.getText().toString().isEmpty() ||
                editYear.getText().toString().isEmpty() ||
                editBlurb.getText().toString().isEmpty() ||
                selectedImageUri == null) {

            Toast.makeText(getContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Buat objek Book baru dengan data dari form
            Book newBook = new Book(
                    editTitle.getText().toString(),
                    editAuthor.getText().toString(),
                    editBlurb.getText().toString(),
                    spinnerGenre.getSelectedItem().toString(),
                    "", // Kosongkan untuk sementara jika ada kolom yang tidak dipakai
                    selectedImageUri.toString(), // Konversi URI ke string
                    Integer.parseInt(editYear.getText().toString()), // Parsing tahun ke int
                    false,
                    0
            );

            // Tambahkan buku ke list
            DataBook.bookList.add(newBook);

            // Kosongkan form setelah buku ditambahkan
            clearForm();
            Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            // Jika tahun tidak valid
            Toast.makeText(getContext(), "Invalid year format", Toast.LENGTH_SHORT).show();
        }
    }

    // Fungsi untuk mengosongkan semua inputan form
    private void clearForm() {
        editTitle.setText("");
        editAuthor.setText("");
        spinnerGenre.setSelection(0);
        editYear.setText("");
        editBlurb.setText("");
        selectedImageUri = null;
        bookImage.setImageResource(0); // Reset gambar
    }
}
