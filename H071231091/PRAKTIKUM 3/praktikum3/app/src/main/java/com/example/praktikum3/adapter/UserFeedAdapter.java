package com.example.praktikum3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum3.R;
import com.example.praktikum3.jclass.Feed;

import java.util.ArrayList;

// Adapter untuk menampilkan daftar Feed (postingan gambar) dalam grid
public class UserFeedAdapter extends RecyclerView.Adapter<UserFeedAdapter.ViewHolder> {

    private ArrayList<Feed> feedList;
    private OnFeedClickListener feedClickListener;
    public interface OnFeedClickListener {
        void onFeedClick(Feed feed);
    }

    // Konstruktor adapter: inisialisasi list dan listener
    public UserFeedAdapter(ArrayList<Feed> feedList, OnFeedClickListener listener) {
        this.feedList = feedList;
        this.feedClickListener = listener;
    }

    // Membuat ViewHolder untuk setiap item di RecyclerView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_grid_post.xml untuk setiap item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid_post, parent, false);
        return new ViewHolder(view);
    }

    // Menghubungkan data feed dengan tampilan item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feed feed = feedList.get(position); // Ambil data Feed pada posisi saat ini

        // Cek apakah gambar berasal dari resource drawable atau URI
        if (feed.isFromResource()) {
            Glide.with(holder.itemView.getContext())
                    .load(feed.getImageRes())
                    .override(300, 300)
                    .into(holder.imageView);
        } else {
            // Jika dari URI (misal: gambar dari galeri atau kamera)
            Glide.with(holder.itemView.getContext())
                    .load(feed.getImageUri())
                    .override(300, 300)
                    .into(holder.imageView);
        }

        // Set event klik pada item
        holder.itemView.setOnClickListener(v -> feedClickListener.onFeedClick(feed));
    }

    // Mengembalikan jumlah item dalam list
    @Override
    public int getItemCount() {
        return feedList.size();
    }

    // ViewHolder adalah class untuk menyimpan view dari item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_grid_image); // Hubungkan ke ImageView di layout
        }
    }
}
