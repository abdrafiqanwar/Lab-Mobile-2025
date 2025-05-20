package com.projeku.tuprak4.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.projeku.tuprak4.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private ArrayList<Book> books;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    private OnItemClickListener listener;

    public BookAdapter(Context context, ArrayList<Book> books, OnItemClickListener listener) {
        this.context = context;
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.title.setText(book.title);
        holder.author.setText(book.author);
        holder.genre.setText(book.genre);
        holder.rating.setText(String.valueOf(book.rating));
        holder.year.setText(String.valueOf(book.year));
        Glide.with(context)
                .load(book.getImageUri())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.coverImage);


        holder.itemView.setOnClickListener(v -> listener.onItemClick(book));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void filterList(ArrayList<Book> filtered) {
        books = filtered;
        notifyDataSetChanged();
    }
    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, genre, rating, year;
        ImageView coverImage;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
            genre = itemView.findViewById(R.id.book_genre);
            rating = itemView.findViewById(R.id.book_rating);
            year = itemView.findViewById(R.id.book_year);
            coverImage = itemView.findViewById(R.id.book_image);

        }
    }
}
