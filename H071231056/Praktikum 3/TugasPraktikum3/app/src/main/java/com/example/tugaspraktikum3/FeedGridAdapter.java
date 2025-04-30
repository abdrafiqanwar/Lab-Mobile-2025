package com.example.tugaspraktikum3;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FeedGridAdapter extends RecyclerView.Adapter<FeedGridAdapter.ViewHolder> {
    private static final String TAG = "FeedGridAdapter";
    private List<User.Feed> feeds;

    public FeedGridAdapter(List<User.Feed> feeds) {
        this.feeds = feeds;
    }

    @NonNull
    @Override
    public FeedGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            // Make sure we're inflating the correct layout
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_feed_profile, parent, false);
            return new ViewHolder(view);
        } catch (Exception e) {
            Log.e(TAG, "Error creating ViewHolder", e);
            // Create a fallback view
            View view = new View(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FeedGridAdapter.ViewHolder holder, int position) {
        try {
            User.Feed feed = feeds.get(position);
            holder.setData(feed);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), FeedDetailActivity.class);

                // Kirim data gambar
                if (feed.hasImageUri()) {
                    intent.putExtra("IMAGE_URI", feed.getImageUri().toString());
                } else {
                    intent.putExtra("IMAGE_RES_ID", feed.getImageResId());
                }

                // Kirim caption
                intent.putExtra("CAPTIONS", feed.getCaption());

                holder.itemView.getContext().startActivity(intent);
            });
        } catch (Exception e) {
            Log.e(TAG, "Error binding ViewHolder", e);
        }
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageFeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFeed = itemView.findViewById(R.id.imageFeed);
        }

        public void setData(User.Feed feed) {
            if (feed.hasImageUri()) {
                Log.d("FeedGridAdapter", "Loading feed image from URI: " + feed.getImageUri());

                // Use Glide for URI images - it handles content:// URIs better than direct ImageView
                Glide.with(itemView.getContext())
                        .load(feed.getImageUri())
                        .placeholder(R.drawable.cheese)
                        .error(R.drawable.bbq_sauce) // Add error indicator
                        .into(imageFeed);
            } else if (feed.getImageResId() != -1) {
                Log.d("FeedGridAdapter", "Loading feed image from resource: " + feed.getImageResId());
                imageFeed.setImageResource(feed.getImageResId());
            } else {
                Log.d("FeedGridAdapter", "No image available for this feed");
                imageFeed.setVisibility(View.GONE);
            }
        }
    }
}