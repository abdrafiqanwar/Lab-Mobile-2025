package com.example.tp4.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4.DetailActivity;
import com.example.tp4.Model.Book;
import com.example.tp4.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Book> books;
    private List<Book> booksFull;
    private List<Book> filteredList;

    private boolean isFavoriteFragment;

    public BookAdapter(Context context, List<Book> books, boolean isFavoriteFragment) {
        this.context = context;
        this.books = books;
        this.booksFull = new ArrayList<>(books); // Simpan data asli untuk filter
        this.filteredList = new ArrayList<>(books);
        this.isFavoriteFragment = isFavoriteFragment;  // Menentukan fragmentnya
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (isFavoriteFragment) {
            view = LayoutInflater.from(context).inflate(R.layout.fav_book_item, parent, false);
            return new BookFavoriteViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
            return new BookHomeViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Book book = books.get(position);

        if (holder instanceof BookHomeViewHolder) {
            BookHomeViewHolder homeViewHolder = (BookHomeViewHolder) holder;

            if (book.getCoverUp() != null) {
                homeViewHolder.image_cover.setImageURI(book.getCoverUp());
            } else {
                homeViewHolder.image_cover.setImageResource(book.getCover());
            }

            homeViewHolder.tvTitle.setText(book.getTitle());
            homeViewHolder.tvAuthor.setText(book.getAuthor());
            homeViewHolder.ratingBar.setRating(book.getRating());
            homeViewHolder.tvGenre.setText(book.getGenre());
            homeViewHolder.getTvAuthor.setText(String.valueOf(book.getRating()));

            homeViewHolder.theBook.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("book", book); // pastikan class Book implements Parcelable
                context.startActivity(intent);
            });
        }

        if (holder instanceof BookFavoriteViewHolder) {
            BookFavoriteViewHolder favViewHolder = (BookFavoriteViewHolder) holder;

            if (book.getCoverUp() != null) {
                favViewHolder.image_cover.setImageURI(book.getCoverUp());
            } else {
                favViewHolder.image_cover.setImageResource(book.getCover());
            }

            favViewHolder.tvTitle.setText(book.getTitle());
            favViewHolder.tvAuthor.setText(book.getAuthor());
            favViewHolder.ratingBar.setRating(book.getRating());
            favViewHolder.tvGenre.setText(book.getGenre());
            favViewHolder.getTvAuthor.setText(String.valueOf(book.getRating()));

            favViewHolder.favBook.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("book", book); // pastikan class Book implements Parcelable
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public void filter(String query, String genre) {
        List<Book> filtered = new ArrayList<>();

        String q = query == null ? "" : query.toLowerCase().trim();
        String g = genre == null || genre.equalsIgnoreCase("Semua") ? "" : genre.toLowerCase().trim();

        for (Book book : booksFull) {
            boolean matchesQuery = book.getTitle().toLowerCase().contains(q) ||
                    book.getAuthor().toLowerCase().contains(q);
            boolean matchesGenre = g.isEmpty() || book.getGenre().toLowerCase().equals(g);

            if (matchesQuery && matchesGenre) {
                filtered.add(book);
            }
        }

        books.clear();
        books.addAll(filtered);
        notifyDataSetChanged();
    }


    // ViewHolder untuk HomeFragment
    public static class BookHomeViewHolder extends RecyclerView.ViewHolder {
        ImageView image_cover;
        TextView tvTitle, tvAuthor;
        LinearLayout theBook;
        RatingBar ratingBar;
        TextView getTvAuthor;
        Chip tvGenre;

        public BookHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            image_cover = itemView.findViewById(R.id.image_book_cover);
            tvTitle = itemView.findViewById(R.id.text_book_title);
            tvAuthor = itemView.findViewById(R.id.text_book_author);
            theBook = itemView.findViewById(R.id.theBook);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            tvGenre = itemView.findViewById(R.id.chip_book_genre);
            getTvAuthor = itemView.findViewById(R.id.text_book_rating);
        }
    }

    // ViewHolder untuk FavBookFragment
    public static class BookFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView image_cover;
        TextView tvTitle, tvAuthor;
        LinearLayout favBook;
        RatingBar ratingBar;
        TextView getTvAuthor;
        Chip tvGenre;

        public BookFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            image_cover = itemView.findViewById(R.id.image_book_cover);
            tvTitle = itemView.findViewById(R.id.text_book_title);
            tvAuthor = itemView.findViewById(R.id.text_book_author);
            favBook = itemView.findViewById(R.id.favBook);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            tvGenre = itemView.findViewById(R.id.chip_book_genre);
            getTvAuthor = itemView.findViewById(R.id.text_book_rating);
        }
    }
}


