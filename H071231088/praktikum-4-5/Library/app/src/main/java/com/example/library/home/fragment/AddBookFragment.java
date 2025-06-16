package com.example.library.home.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.library.data.classjava.Book;
import com.example.library.home.ui.BookViewModel;
import com.example.library.R;
import com.example.library.databinding.FragmentAddBookBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddBookFragment extends Fragment {

    private FragmentAddBookBinding binding;
    private BookViewModel viewModel;
    private List<String> selectedGenres = new ArrayList<>();
    private Uri selectedImageUri = null;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ArrayAdapter<String> genreAdapter;
    private Set<String> cachedGenres = new HashSet<>();
    private boolean isDialogShowing = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        binding.bookInput.setImageURI(selectedImageUri);
                    }
                }
        );

        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.inflate(inflater, container, false);

        // Siapkan Spinner dengan satu opsi
        List<String> genreOptions = new ArrayList<>();
        genreOptions.add("Pilih Genre");

        genreAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                genreOptions
        );
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGenre.setAdapter(genreAdapter);

        binding.spinnerGenre.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_UP && !isDialogShowing) {
                showGenreDialog(cachedGenres);
            }
            return false;
        });

        binding.bookInput.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        binding.btnSubmit.setOnClickListener(v -> {
            if (validateInputs()) {
                Book book = createBookFromInputs();
                viewModel.addBook(book);
                updateGenreSpinner();
                clearInputs();
            }
        });

        cachedGenres = getUniqueGenres();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Set<String> getUniqueGenres() {
        Set<String> genres = new HashSet<>();
        List<Book> books = viewModel.getBookList().getValue();
        if (books != null) {
            for (Book book : books) {
                genres.addAll(book.getGenres());
            }
        }
        Log.d("AddBookFragment", "Unique genres: " + genres);
        return genres;
    }

    private void showGenreDialog(Set<String> genres) {
        if (isDialogShowing) return;
        isDialogShowing = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Pilih Genre");

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        List<CheckBox> checkBoxes = new ArrayList<>();
        for (String genre : genres) {
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setText(genre);
            checkBox.setChecked(selectedGenres.contains(genre));
            layout.addView(checkBox);
            checkBoxes.add(checkBox);
        }

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            selectedGenres.clear();
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    selectedGenres.add(checkBox.getText().toString());
                }
            }
            updateGenreSpinner();
            Log.d("AddBookFragment", "Selected genres: " + selectedGenres);
            isDialogShowing = false;
            dialog.dismiss();
        });

        builder.setNegativeButton("Batal", (dialog, which) -> {
            isDialogShowing = false;
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateGenreSpinner() {
        cachedGenres = getUniqueGenres();
        List<String> genreOptions = new ArrayList<>();
        if (selectedGenres.isEmpty()) {
            genreOptions.add("Pilih Genre");
        } else {
            genreOptions.add(String.join(", ", selectedGenres));
        }
        genreAdapter.clear();
        genreAdapter.addAll(genreOptions);
        genreAdapter.notifyDataSetChanged();
        binding.spinnerGenre.setSelection(0);
    }

    private void clearInputs() {
        binding.inputJudul.setText("");
        binding.inputAuthor.setText("");
        binding.inputTahun.setText("");
        binding.inputCustomGenre.setText("");
        selectedGenres.clear();
        selectedImageUri = null;
        binding.bookInput.setImageResource(R.drawable.ic_launcher_background);
        updateGenreSpinner();

    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (binding.inputJudul.getText().toString().trim().isEmpty()) {
            binding.tilJudul.setError("Judul tidak boleh kosong");
            isValid = false;
        } else {
            binding.tilJudul.setError(null);
        }

        if (binding.inputAuthor.getText().toString().trim().isEmpty()) {
            binding.tilAuthor.setError("Penulis tidak boleh kosong");
            isValid = false;
        } else {
            binding.tilAuthor.setError(null);
        }

        String tahun = binding.inputTahun.getText().toString().trim();
        if (tahun.isEmpty()) {
            binding.tilTahun.setError("Tahun tidak boleh kosong");
            isValid = false;
        } else {
            try {
                int year = Integer.parseInt(tahun);
                if (year < 1800 || year > 2025) {
                    binding.tilTahun.setError("Tahun harus antara 1800-2025");
                    isValid = false;
                } else {
                    binding.tilTahun.setError(null);
                }
            } catch (NumberFormatException e) {
                binding.tilTahun.setError("Masukkan tahun yang valid");
                isValid = false;
            }
        }

        if (selectedGenres.isEmpty() && binding.inputCustomGenre.getText().toString().trim().isEmpty()) {
            TextView errorText = (TextView) binding.spinnerGenre.getSelectedView();
            if (errorText != null) {
                errorText.setError("Pilih setidaknya satu genre");
            }
            binding.spinnerGenre.performClick();
            isValid = false;
        } else {
            TextView errorText = (TextView) binding.spinnerGenre.getSelectedView();
            if (errorText != null) {
                errorText.setError(null);
            }
        }

        return isValid;
    }

    private Book createBookFromInputs() {
        String title = binding.inputJudul.getText().toString().trim();
        String author = binding.inputAuthor.getText().toString().trim();
        int tahun = Integer.parseInt(binding.inputTahun.getText().toString().trim());

        List<String> genres = new ArrayList<>(selectedGenres);
        String customGenres = binding.inputCustomGenre.getText().toString().trim();
        if (!customGenres.isEmpty()) {
            genres.addAll(Arrays.asList(customGenres.split("\\s*,\\s*")));
        }

        String coverUri = selectedImageUri != null ? selectedImageUri.toString() : "";

        int id = generateBookId();
        int likeCount = 0;
        float rating = 0.0f;
        long addedTimestamp = System.currentTimeMillis();
        String description = binding.inputDescription.getText().toString().trim();// Tambahkan timestamp

        return new Book(id, title, author, tahun, coverUri, likeCount, rating, genres, addedTimestamp, description);
    }

    private int generateBookId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}