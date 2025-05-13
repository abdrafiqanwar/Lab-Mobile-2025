package com.example.tp04fragment.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp04fragment.Models.Book;
import com.example.tp04fragment.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private ArrayList<Book> books;
    private OnBookClickListener listener;

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
        holder.cover.setImageResource(book.getCover());
        holder.judul.setText(book.getJudul());
        holder.penulis.setText(book.getPenulis());
        holder.bookRating.setText(String.valueOf(book.getRating()));

        if (book.getCoverUri() != null) {
            holder.cover.setImageURI(book.getCoverUri());
        } else if (book.getCover() != 0) {
            holder.cover.setImageResource(book.getCover());
        }

        holder.itemView.setOnClickListener(v -> listener.onBookClick(book));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void filterList(ArrayList<Book> filteredList) {
        books = filteredList;
        notifyDataSetChanged();
    }

    public void updateBooks(ArrayList<Book> newBooks) {
        this.books = newBooks;
        notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView judul, penulis, bookRating;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            judul = itemView.findViewById(R.id.judul);
            penulis = itemView.findViewById(R.id.penulis);
            bookRating = itemView.findViewById(R.id.bookRating);
        }
    }

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }
}