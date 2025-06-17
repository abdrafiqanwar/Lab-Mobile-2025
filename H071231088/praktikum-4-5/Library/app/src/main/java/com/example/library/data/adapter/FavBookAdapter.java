package com.example.library.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.data.classjava.Book;
import com.example.library.home.ui.BookDetailActivity;
import com.example.library.data.classjava.FavBook;
import com.example.library.R;
import com.example.library.databinding.ItemFavBookBinding;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FavBookAdapter extends RecyclerView.Adapter<FavBookAdapter.ViewHolder> {
    private final Context context;
    private final List<FavBook> favBooks;

    public FavBookAdapter(Context context, List<FavBook> favBooks) {
        this.context = context;
        this.favBooks = favBooks;
        sortByTimestampDesc();
    }

    public void updateBooks(List<FavBook> newBooks) {
        favBooks.clear();
        favBooks.addAll(newBooks);
        sortByTimestampDesc(); // Urutkan ulang setiap update
        notifyDataSetChanged();
    }

    private void sortByTimestampDesc() {
        Collections.sort(favBooks, new Comparator<FavBook>() {
            @Override
            public int compare(FavBook b1, FavBook b2) {
                return Long.compare(b2.getAddedTimestamp(), b1.getAddedTimestamp()); // Terbaru duluan
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemFavBookBinding binding;

        public ViewHolder(@NonNull ItemFavBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemFavBookBinding binding = ItemFavBookBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavBook favBook = favBooks.get(position);

        holder.binding.tvTitle.setText(favBook.getTitle());
        holder.binding.tvAuthor.setText(favBook.getAuthor());
        holder.binding.tvGenres.setText(favBook.getGenres().stream().collect(Collectors.joining(", ")));
        holder.binding.tvRating.setText(String.valueOf(favBook.getRating()));
        holder.binding.tvLikeCount.setText(String.valueOf(favBook.getLikeCount()));

        String coverUri = favBook.getCoverBookUri();
        Log.d("FavBookAdapter", "Cover URI: " + coverUri);

        if (coverUri != null && !coverUri.isEmpty()) {
            if (coverUri.startsWith("drawable/")) {
                int imageResId = context.getResources().getIdentifier(
                        coverUri.replace("drawable/", ""),
                        "drawable",
                        context.getPackageName()
                );
                if (imageResId != 0) {
                    holder.binding.ivCover.setImageResource(imageResId);
                } else {
                    Log.e("FavBookAdapter", "Drawable resource not found: " + coverUri);
                    holder.binding.ivCover.setImageResource(R.drawable.ic_launcher_background);
                }
            } else {
                try {
                    Uri imageUri = Uri.parse(coverUri);
                    holder.binding.ivCover.setImageURI(imageUri);
                } catch (Exception e) {
                    Log.e("FavBookAdapter", "Error loading image URI: " + coverUri, e);
                    holder.binding.ivCover.setImageResource(R.drawable.ic_launcher_background);
                }
            }
        } else {
            holder.binding.ivCover.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            Book book = new Book(
                    favBook.getId(),
                    favBook.getTitle(),
                    favBook.getAuthor(),
                    favBook.getTahunRilis(),
                    favBook.getCoverBookUri(),
                    favBook.getLikeCount(),
                    favBook.getRating(),
                    favBook.getGenres(),
                    System.currentTimeMillis(),
                    favBook.getDescription()
            );

            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra("book", book);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favBooks.size();
    }
}
