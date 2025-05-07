package com.example.praktikum3.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum3.R;
import com.example.praktikum3.activity.PostDetailActivity;

import java.util.ArrayList;

// Adapter ini digunakan untuk menampilkan feed (postingan) milik user lain dalam bentuk grid
public class OtherUserFeedAdapter extends RecyclerView.Adapter<OtherUserFeedAdapter.ViewHolder> {

    private final ArrayList<Integer> postList;
    private final Context context;
    private final String username;
    private final int userProfileImageRes;


    // Konstruktor adapter: inisialisasi context, postList, username, dan foto profil
    public OtherUserFeedAdapter(Context context, ArrayList<Integer> postList, String username, int userProfileImageRes) {
        this.context = context;
        this.postList = postList != null ? postList : new ArrayList<>();
        this.username = username;
        this.userProfileImageRes = userProfileImageRes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_grid_post.xml ke dalam objek View
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int imageRes = postList.get(position);

        // Tampilkan gambar ke imageView menggunakan Glide
        Glide.with(context)
                .load(imageRes)
                .into(holder.imageView);

        // Saat gambar diklik, buka PostDetailActivity dan kirim data gambar + user
        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("feed_image", imageRes);
            intent.putExtra("username", username);
            intent.putExtra("user_profile_image", userProfileImageRes);
            context.startActivity(intent);
        });
    }

    // Mengembalikan jumlah item (berapa banyak postingan yang ditampilkan)
    @Override
    public int getItemCount() {
        return postList.size();
    }

    // ViewHolder: merepresentasikan satu item grid (satu gambar)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // Komponen ImageView untuk menampilkan gambar

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Hubungkan dengan ImageView di layout item_grid_post.xml
            imageView = itemView.findViewById(R.id.iv_grid_image);
        }
    }
}
