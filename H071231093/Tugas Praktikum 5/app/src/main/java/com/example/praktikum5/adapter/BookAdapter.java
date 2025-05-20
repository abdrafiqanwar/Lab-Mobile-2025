package com.example.praktikum5.adapter;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum5.R;
import com.example.praktikum5.jclass.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> implements Filterable {
    private final Context context;
    private final List<Book> originalBooks;
    private List<Book> filteredBooks;
    private final OnBookClickListener listener;
    private String selectedGenre = "All";

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public BookAdapter(Context context, List<Book> books, OnBookClickListener listener) {
        this.context = context;
        this.originalBooks = new ArrayList<>(books);
        this.filteredBooks = books;
        this.listener = listener;
    }

    public void setSelectedGenre(String genre) {
        this.selectedGenre = genre;
        getFilter().filter("");
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = filteredBooks.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        Glide.with(context)
                .load(book.getCoverImageUri())
                .into(holder.coverImageView);

        holder.coverImageView.setOnClickListener(v -> listener.onBookClick(book));
    }

    @Override
    public int getItemCount() {
        return filteredBooks.size();
    }

    @Override
    public Filter getFilter() {
        return bookFilter;
    }

    private final Filter bookFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = (constraint == null)
                    ? ""
                    : constraint.toString().toLowerCase().trim();
            List<Book> tempList = new ArrayList<>();

            for (Book book : originalBooks) {
                boolean matchesQuery = query.isEmpty() || book.getTitle().toLowerCase().contains(query);
                boolean matchesGenre = selectedGenre.equals("All") || book.getGenre().equalsIgnoreCase(selectedGenre);

                if (matchesQuery && matchesGenre) {
                    tempList.add(book);
                }
            }

            FilterResults results = new FilterResults();
            results.values = tempList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredBooks = (List<Book>) results.values;
            notifyDataSetChanged();
        }
    };

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImageView;
        TextView titleTextView, authorTextView;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.image_view_cover);
            titleTextView  = itemView.findViewById(R.id.text_view_title);
            authorTextView = itemView.findViewById(R.id.text_view_author);
        }
    }
}