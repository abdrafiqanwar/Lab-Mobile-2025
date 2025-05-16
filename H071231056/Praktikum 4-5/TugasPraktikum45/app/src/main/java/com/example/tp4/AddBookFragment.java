package com.example.tp4;

import android.app.Activity;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tp4.databook.Book;
import com.example.tp4.databook.BookDataManager;
import com.google.android.material.slider.Slider;

public class AddBookFragment extends Fragment {

    private EditText titleEditText, authorEditText, yearEditText, blurbEditText;
    private Spinner genreSpinner;
    private Slider ratingSlider;
    private ImageView coverImageView;
    private Button addCoverButton, saveButton;

    private Uri selectedImageUri = null;
    private BookDataManager bookDataManager;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookDataManager = new BookDataManager();

        // Register image picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        coverImageView.setImageURI(selectedImageUri);
                        coverImageView.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        // Set the action bar title (if using activity's action bar)
        if (getActivity() != null && ((MainActivity)getActivity()).getSupportActionBar() != null) {
            ((MainActivity)getActivity()).getSupportActionBar().setTitle("Add New Book");
        }

        // Initialize UI components
        titleEditText = view.findViewById(R.id.addTitleEditText);
        authorEditText = view.findViewById(R.id.addAuthorEditText);
        yearEditText = view.findViewById(R.id.addYearEditText);
        blurbEditText = view.findViewById(R.id.addBlurbEditText);
        genreSpinner = view.findViewById(R.id.addGenreSpinner);
        ratingSlider = view.findViewById(R.id.addRatingSlider);
        coverImageView = view.findViewById(R.id.addCoverImageView);
        addCoverButton = view.findViewById(R.id.addCoverButton);
        saveButton = view.findViewById(R.id.saveBookButton);

        // Set up genre spinner
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                bookDataManager.getAllGenres());
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);

        // Set up button listeners
        addCoverButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        saveButton.setOnClickListener(v -> saveBook());

        return view;
    }

    private void saveBook() {
        // Validate inputs
        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String yearStr = yearEditText.getText().toString().trim();
        String blurb = blurbEditText.getText().toString().trim();
        String genre = genreSpinner.getSelectedItem().toString();
        float rating = ratingSlider.getValue();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || blurb.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri == null) {
            Toast.makeText(getContext(), "Please select a cover image", Toast.LENGTH_SHORT).show();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter a valid year", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate a new ID for the book
        int newId = BookDataManager.getNextId();

        // Create new book
        Book newBook = new Book(newId, title, author, year, blurb, selectedImageUri, false, genre, rating);

        // Add to data manager
        BookDataManager.addBook(newBook);

        // Show success message
        Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to home fragment
        if (getActivity() != null) {
            ((MainActivity)getActivity()).loadFragment(new HomeFragment());
        }
    }
}