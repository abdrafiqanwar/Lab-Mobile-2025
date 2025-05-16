package com.example.tp4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp4.databook.Book;

public class DetailBookActivity extends AppCompatActivity {
    private ImageView coverImageView;
    private TextView titleTextView;
    private TextView authorTextView;
    private TextView yearTextView;
    private TextView blurbTextView;
    private TextView genreTextView;
    private RatingBar ratingBar;
    private Button likeButton;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_book);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Book Details");
        }


        coverImageView = findViewById(R.id.detailCoverImageView);
        titleTextView = findViewById(R.id.detailTitleTextView);
        authorTextView = findViewById(R.id.detailAuthorTextView);
        yearTextView = findViewById(R.id.detailYearTextView);
        blurbTextView = findViewById(R.id.detailBlurbTextView);
        genreTextView = findViewById(R.id.detailGenreTextView);
        ratingBar = findViewById(R.id.detailRatingBar);
        likeButton = findViewById(R.id.likeButton);

        book = getIntent().getParcelableExtra("BOOK");
        if (book != null) {
            displayBookDetails();
        } else {
            finish();
        }

    }

    private void displayBookDetails() {
        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        yearTextView.setText(String.valueOf(book.getPublishYear()));
        blurbTextView.setText(book.getBlurb());
        genreTextView.setText(book.getGenre());
        ratingBar.setRating(book.getRating());

        coverImageView.setImageURI(book.getCoverImage());

        updateLikeButtonState();

        likeButton.setOnClickListener(v -> {
            book.toggleLike();
            updateLikeButtonState();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("UPDATED_BOOK", book);
            setResult(RESULT_OK, resultIntent);
        });


    }

    private void updateLikeButtonState() {
        if (book.isLiked()) {
            likeButton.setText("Unlike");
        } else {
            likeButton.setText("Like");
        }
    }
}