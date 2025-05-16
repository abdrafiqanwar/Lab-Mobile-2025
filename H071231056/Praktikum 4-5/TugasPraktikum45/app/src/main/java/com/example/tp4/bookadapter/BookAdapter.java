package com.example.tp4.bookadapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4.DetailBookActivity;
import com.example.tp4.R;
import com.example.tp4.databook.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private static ArrayList<Book> books;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public BookAdapter(ArrayList<Book> books, OnBookClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.titleTextView.setText("Judul : " + book.getTitle());
        holder.authorTextView.setText("by " + book.getAuthor());
        holder.yearTextView.setText("Tahun Terbit : " + String.valueOf(book.getPublishYear()));
        holder.genreTextView.setText("Genre : "  + book.getGenre());
        holder.ratingTextView.setText(String.format("%.1f★", book.getRating()));
        holder.likeIndicator.setVisibility(book.isLiked() ? View.VISIBLE : View.GONE);

        Uri coverUri = book.getCoverImage();
        if (coverUri != null) {
            holder.coverImageView.setImageURI(coverUri);
        }

        // Set click listener
        holder.cardView.setOnClickListener(v -> {
            listener.onBookClick(book);
        });


    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void updateBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, authorTextView, yearTextView, genreTextView, ratingTextView;
        public ImageView likeIndicator, coverImageView;
        public View cardView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            genreTextView = itemView.findViewById(R.id.genreTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            likeIndicator = itemView.findViewById(R.id.likeIndicator);
            coverImageView = itemView.findViewById(R.id.coverImageView);
        }
    }
}
