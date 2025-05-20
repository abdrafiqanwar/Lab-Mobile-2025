package com.example.praktikum_4;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    interface OnBookClickListener {
        void onBookClick(Book book);
    }

    private final List<Book> bookList;
    private final OnBookClickListener listener;

    public BookAdapter(List<Book> bookList, OnBookClickListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.title.setText(book.title);
        holder.author.setText(book.author);
        if (book.coverBitmap != null) {
            holder.cover.setImageBitmap(book.coverBitmap);
        } else {
            holder.cover.setImageResource(book.coverResId);
        }
        holder.itemView.setOnClickListener(v -> listener.onBookClick(book));
    }

    @Override public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title, author;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.book_cover);
            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
        }
    }
}
