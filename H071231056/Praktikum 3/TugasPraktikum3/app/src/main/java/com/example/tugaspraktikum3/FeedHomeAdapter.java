package com.example.tugaspraktikum3;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedHomeAdapter extends RecyclerView.Adapter<FeedHomeAdapter.ViewHolder> {

    private ArrayList<User.Feed> feeds;

    public FeedHomeAdapter(ArrayList<User.Feed> feeds) {
        this.feeds = feeds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User.Feed feed = feeds.get(position);
        holder.setData(feed);
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageProfile, imageFeed;
        private TextView textUsername, textCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.imageProfile);
            textUsername = itemView.findViewById(R.id.textUsername);
            imageFeed = itemView.findViewById(R.id.imageFeed);
            textCaption = itemView.findViewById(R.id.textCaption);

            imageProfile.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String username = feeds.get(position).getUsername();
                    openProfileActivity(username);
                }
            });

            textUsername.setOnClickListener(v -> {
                String username = feeds.get(getAdapterPosition()).getUsername();
                openProfileActivity(username);
            });


        }

        private void openProfileActivity(String username) {
            Intent intent = new Intent(itemView.getContext(), ProfileActivity.class);
            intent.putExtra("USERNAME", username);
            itemView.getContext().startActivity(intent);
        }

        public void setData(User.Feed feed) {
            // Ambil username dari Feed
            String username = feed.getUsername();
            textUsername.setText(username);

            // Cari User berdasarkan username di DataDummy
            User user = findUserByUsername(username);
            if (user != null) {
                Picasso.get()
                        .load(user.getProfileImageUrl())
                        .placeholder(R.drawable.bbq_sauce)
                        .into(imageProfile);
            } else {
                imageProfile.setImageResource(R.drawable.bbq_sauce);
            }

            // Tampilkan caption dan gambar feed
            textCaption.setText(feed.getCaption());

            if (feed.hasImageUri()) {
                Log.d("FeedHomeAdapter", "Loading feed image from URI: " + feed.getImageUri());
                Picasso.get()
                        .load(feed.getImageUri())
                        .placeholder(R.drawable.bbq_sauce)
                        .error(R.drawable.chicken_patty) // Add error image
                        .into(imageFeed);
            } else {
                Log.d("FeedHomeAdapter", "Loading feed image from resource: " + feed.getImageResId());
                imageFeed.setImageResource(feed.getImageResId());
            }
        }

        // Helper method untuk mencari User
        private User findUserByUsername(String username) {
            for (User user : DataDummy.getDummyUsers()) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
            return null;
        }



    }
}
