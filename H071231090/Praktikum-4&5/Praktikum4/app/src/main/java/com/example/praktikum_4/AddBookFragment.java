package com.example.praktikum_4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.*;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class AddBookFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText inputTitle, inputAuthor, inputYear, inputBlurb;
    private ImageView coverPreview;
    private Button selectImageBtn, addBookBtn;

    private Bitmap selectedCoverBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        inputTitle = view.findViewById(R.id.input_title);
        inputAuthor = view.findViewById(R.id.input_author);
        inputYear = view.findViewById(R.id.input_year);
        inputBlurb = view.findViewById(R.id.input_blurb);
        coverPreview = view.findViewById(R.id.cover_preview);
        selectImageBtn = view.findViewById(R.id.btn_select_image);
        addBookBtn = view.findViewById(R.id.btn_add_book);

        // Buka file manager untuk pilih gambar
        selectImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih Cover Buku"), PICK_IMAGE_REQUEST);
        });

        // Tombol tambah buku
        addBookBtn.setOnClickListener(v -> {
            String title = inputTitle.getText().toString();
            String author = inputAuthor.getText().toString();
            String year = inputYear.getText().toString();
            String blurb = inputBlurb.getText().toString();

            if (title.isEmpty() || author.isEmpty() || year.isEmpty() || blurb.isEmpty() || selectedCoverBitmap == null) {
                Toast.makeText(getContext(), "Silakan lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Untuk sementara kita gunakan gambar default, kamu bisa simpan Bitmap kalau mau disimpan permanen
            Book newBook = new Book(title, author, year, blurb, selectedCoverBitmap, false);

            BookData.books.add(newBook);

            // Kembali ke HomeFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        return view;
    }

    // Proses hasil pemilihan gambar
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedCoverBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                coverPreview.setImageBitmap(selectedCoverBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
