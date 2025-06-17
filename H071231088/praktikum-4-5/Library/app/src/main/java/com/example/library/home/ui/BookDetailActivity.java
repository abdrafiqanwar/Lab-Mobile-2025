package com.example.library.home.ui;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.library.R;
import com.example.library.data.classjava.Book;
import com.example.library.data.classjava.FavBook;
import com.example.library.data.repository.FavBookRepository;
import com.example.library.databinding.ActivityBookDetailBinding;

import java.util.stream.Collectors;

public class BookDetailActivity extends AppCompatActivity {

    private ActivityBookDetailBinding binding;
    private BookViewModel viewModel;
    private Book book;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(BookViewModel.class);

        book = getIntent().getParcelableExtra("book");
        if (book != null) {
            updateUI(book);
        }

        binding.btnaddangremoveFavorite.setText("Add to Fav");
        isFavorite = FavBookRepository.isFavorite(book.getId());
        updateFavoriteButton();

        binding.btnaddangremoveFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;

            if (isFavorite) {
                FavBook favBook = new FavBook(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getTahunRilis(),
                        book.getCoverBookUri(),
                        book.getLikeCount(),
                        book.getRating(),
                        book.getGenres(),
                        System.currentTimeMillis(),
                        book.getDescription()
                );
                FavBookRepository.add(favBook);
            } else {
                FavBookRepository.removeById(book.getId());
//                FavBookRepository.getAll().observe(this, favBooks -> {
//                    finish();
//                });
                finish();
            }
            updateFavoriteButton();
        });
    }

    private void updateUI(Book book) {
        binding.tvTitle.setText(book.getTitle());
        binding.tvAuthor.setText("Penulis: " + book.getAuthor());
        binding.tvTahun.setText("Tahun: " + book.getTahunRilis());
        binding.tvGenres.setText("Genre: " + book.getGenres().stream().collect(Collectors.joining(", ")));
        binding.tvRating.setText("Rating: " + book.getRating());
        binding.tvLikeCount.setText("Jumlah Like: " + book.getLikeCount());
        binding.tvDescription.setText("Description: " + book.getDescription());

        String coverUri = book.getCoverBookUri();
        Log.d("BookDetailActivity", "Cover URI: " + coverUri);

        if (coverUri != null && !coverUri.isEmpty()) {
            if (coverUri.startsWith("drawable/")) {
                int imageResId = getResources().getIdentifier(
                        coverUri.replace("drawable/", ""),
                        "drawable",
                        getPackageName()
                );
                if (imageResId != 0) {
                    binding.imgCover.setImageResource(imageResId);
                } else {
                    Log.e("BookDetailActivity", "Drawable resource not found: " + coverUri);
                    binding.imgCover.setImageResource(R.drawable.ic_launcher_background);
                }
            } else {
                try {
                    Uri imageUri = Uri.parse(coverUri);
                    binding.imgCover.setImageURI(imageUri);
                } catch (Exception e) {
                    Log.e("BookDetailActivity", "Error loading image URI: " + coverUri, e);
                    binding.imgCover.setImageResource(R.drawable.ic_launcher_background);
                }
            }
        } else {
            binding.imgCover.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    private void updateFavoriteButton() {
        if (isFavorite) {
            binding.btnaddangremoveFavorite.setText("Remove from Fav");
        } else {
            binding.btnaddangremoveFavorite.setText("Add to Fav");
        }
    }
}