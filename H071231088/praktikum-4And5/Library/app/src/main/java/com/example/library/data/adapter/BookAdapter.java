package com.example.library.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.library.data.classjava.Book;
import com.example.library.home.ui.BookDetailActivity;
import com.example.library.R;
import com.example.library.databinding.ItemBookBinding;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final Context context;
    private List<Book> bookList = new ArrayList<>();

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = (bookList != null) ? bookList : new ArrayList<>();
    }

    public void updateBooks(List<Book> newBooks) {
        this.bookList = new ArrayList<>(newBooks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.binding.tvJudul.setText(book.getTitle());
        holder.binding.tvAuthor.setText(book.getAuthor());
        holder.binding.tvTahun.setText("Tahun: " + book.getTahunRilis());

        // Tangani coverBookUri (URI atau drawable resource)
        String coverUri = book.getCoverBookUri();
        if (coverUri != null && !coverUri.isEmpty()) {
            if (coverUri.startsWith("content://") || coverUri.startsWith("file://")) {
                // URI dari galeri
                Glide.with(context)
                        .load(Uri.parse(coverUri))
                        .placeholder(R.drawable.cover_book)
                        .error(R.drawable.cover_book)
                        .into(holder.binding.imgCover);
            } else {
                // Resource drawable
                int imageResId = context.getResources().getIdentifier(
                        coverUri.replace("drawable/", ""),
                        "drawable",
                        context.getPackageName()
                );
                if (imageResId != 0) {
                    Glide.with(context)
                            .load(imageResId)
                            .placeholder(R.drawable.cover_book)
                            .error(R.drawable.cover_book)
                            .into(holder.binding.imgCover);
                } else {
                    holder.binding.imgCover.setImageResource(R.drawable.cover_book);
                }
            }
        } else {
            holder.binding.imgCover.setImageResource(R.drawable.cover_book);
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra("book", book);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        final ItemBookBinding binding;

        public BookViewHolder(@NonNull ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}