package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.FeedDetailActivity;
import com.example.instagram.R;
import com.example.instagram.UserPost;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private final Context context;
    private final ArrayList<UserPost> userPosts;

    public FeedAdapter(Context context, ArrayList<UserPost> userPosts) {
        this.context = context;
        this.userPosts = userPosts;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed_grid, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        UserPost userPost = userPosts.get(position);
        Uri imageUri = Uri.parse(userPost.getFeedUris());
        holder.imageView.setImageURI(imageUri);

        // Klik feed untuk pindah ke halaman detail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FeedDetailActivity.class);
            intent.putExtra("imageUri", userPost.getFeedUris());
            intent.putExtra("caption", userPost.getCaption());
            intent.putExtra("likes", userPost.getLikes());
            intent.putExtra("comments", userPost.getComments());
            intent.putExtra("time", userPost.getTime());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userPosts.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public FeedViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.feedImage);
        }
    }
}