package com.example.tp4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.tp4.Model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

public class AddBookFragment extends Fragment {
    private ImageView imgCoverPreview;
    private Button btnSelectCover, btnUploadBuku;
    private TextInputEditText edtJudulBuku, edtAuthor, edtTahunRilis, edtBlurb;
    private MaterialAutoCompleteTextView genreDropdown;
    private Uri selectedImageUri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        imgCoverPreview = view.findViewById(R.id.imgCoverPreview);
        btnSelectCover = view.findViewById(R.id.btnSelectCover);
        btnUploadBuku = view.findViewById(R.id.btnUploadBuku);
        edtJudulBuku = view.findViewById(R.id.edtJudulBuku);
        edtAuthor = view.findViewById(R.id.edtAuthor);
        edtTahunRilis = view.findViewById(R.id.edtTahunRilis);
        edtBlurb = view.findViewById(R.id.edtBlurb);
        genreDropdown = view.findViewById(R.id.genreDropdown);

        // Setup genre dropdown
        String[] genres = {"Fiction", "Non-fiction", "Fantasy", "Biography", "Science", "History", "Romance", "Horror"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, genres);
        genreDropdown.setAdapter(adapter);

        btnSelectCover.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            pickIntent.setType("image/*");
            pickIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(pickIntent, 100);
        });

        btnUploadBuku.setOnClickListener(v -> {
            String title = edtJudulBuku.getText().toString().trim();
            String author = edtAuthor.getText().toString().trim();
            String yearStr = edtTahunRilis.getText().toString().trim();
            String blurb = edtBlurb.getText().toString().trim();
            String genre = genreDropdown.getText().toString().trim();

            if (selectedImageUri == null || title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || genre.isEmpty()) {
                Toast.makeText(getContext(), "Harap lengkapi semua field dan pilih gambar", Toast.LENGTH_SHORT).show();
                return;
            }

            int releaseYear = Integer.parseInt(yearStr);
            Book newBook = new Book(title, author, releaseYear, blurb, selectedImageUri, genre, 0.0f);
            ((MainActivity) getActivity()).addBook(newBook);

            Toast.makeText(getContext(), "Buku berhasil ditambahkan", Toast.LENGTH_SHORT).show();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();


            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.nav_home);

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == getActivity().RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imgCoverPreview.setImageURI(selectedImageUri);
        }
    }
}
