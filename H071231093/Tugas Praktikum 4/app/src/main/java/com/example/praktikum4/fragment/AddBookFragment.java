package com.example.praktikum4.fragment;

import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.praktikum4.R;
import com.example.praktikum4.data.BookData;
import com.example.praktikum4.jclass.Book;

public class AddBookFragment extends Fragment {

    private Uri coverImageUri;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        EditText titleEditText = view.findViewById(R.id.edit_text_title);
        EditText authorEditText = view.findViewById(R.id.edit_text_author);
        EditText yearEditText = view.findViewById(R.id.edit_text_year);
        EditText synopsisEditText = view.findViewById(R.id.edit_text_synopsis);
        EditText ratingEditText = view.findViewById(R.id.edit_text_rating);
        Spinner genreSpinner = view.findViewById(R.id.spinner_genre);
        ImageView coverPreview = view.findViewById(R.id.image_view_cover_preview);
        Button addButton = view.findViewById(R.id.button_add_book);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.genre_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == requireActivity().RESULT_OK && result.getData() != null) {
                        coverImageUri = result.getData().getData();
                        coverPreview.setVisibility(View.VISIBLE);
                        Glide.with(this).load(coverImageUri).into(coverPreview);
                    }
                }
        );

        coverPreview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        addButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String author = authorEditText.getText().toString().trim();
            String year = yearEditText.getText().toString().trim();
            String synopsis = synopsisEditText.getText().toString().trim();
            String rating = ratingEditText.getText().toString().trim();
            String genre = genreSpinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(year) ||
                    TextUtils.isEmpty(synopsis) || TextUtils.isEmpty(rating) || coverImageUri == null) {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            Book newBook = new Book(title, author, Integer.parseInt(year), synopsis, coverImageUri.toString(),
                    false, Float.parseFloat(rating), genre);
            BookData.addBook(newBook);

            Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();

            titleEditText.setText("");
            authorEditText.setText("");
            yearEditText.setText("");
            synopsisEditText.setText("");
            ratingEditText.setText("");
            coverPreview.setVisibility(View.GONE);
            coverImageUri = null;
        });

        return view;
    }
}