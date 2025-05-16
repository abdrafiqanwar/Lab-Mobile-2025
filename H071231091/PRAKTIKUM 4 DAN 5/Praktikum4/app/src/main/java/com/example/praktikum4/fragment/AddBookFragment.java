package com.example.praktikum4.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.praktikum4.R;
import com.example.praktikum4.data.BookData;
import com.example.praktikum4.jclass.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddBookFragment extends Fragment {

    private Uri coverImageUri;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        // Inisialisasi semua view dari layout
        EditText titleEditText = view.findViewById(R.id.edit_text_title);
        EditText authorEditText = view.findViewById(R.id.edit_text_author);
        EditText yearEditText = view.findViewById(R.id.edit_text_year);
        EditText synopsisEditText = view.findViewById(R.id.edit_text_synopsis);
        EditText ratingEditText = view.findViewById(R.id.edit_text_rating);
        Spinner genreSpinner = view.findViewById(R.id.spinner_genre);
        ImageView coverPreview = view.findViewById(R.id.image_view_cover_preview);
        Button addButton = view.findViewById(R.id.button_add_book);

        // Setup spinner genre
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.genre_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        // Setup image picker launcher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == requireActivity().RESULT_OK && result.getData() != null) {
                        coverImageUri = result.getData().getData(); // Dapatkan URI gambar yang dipilih
                        coverPreview.setVisibility(View.VISIBLE);
                        try {
                            // Konversi URI ke Bitmap dan tampilkan di ImageView
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                    requireActivity().getContentResolver(),
                                    coverImageUri
                            );
                            coverPreview.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        //  buka gallery untuk memilih gambar
        coverPreview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        // Ketika tombol "Add Book" ditekan
        addButton.setOnClickListener(v -> {
            // Ambil semua input dari form
            String title = titleEditText.getText().toString().trim();
            String author = authorEditText.getText().toString().trim();
            String year = yearEditText.getText().toString().trim();
            String synopsis = synopsisEditText.getText().toString().trim();
            String rating = ratingEditText.getText().toString().trim();
            String genre = genreSpinner.getSelectedItem().toString();

            // Validasi: semua field harus diisi
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(year) ||
                    TextUtils.isEmpty(synopsis) || TextUtils.isEmpty(rating) || coverImageUri == null) {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buat objek Book baru dan tambahkan ke BookData
            Book newBook = new Book(
                    title,
                    author,
                    Integer.parseInt(year),
                    synopsis,
                    coverImageUri.toString(),
                    false, // isFavorite (default false)
                    Float.parseFloat(rating),
                    genre
            );
            BookData.addBook(newBook);

            Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();

            // Kembali ke HomeFragment setelah menambahkan buku
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}