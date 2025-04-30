package com.example.tp3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.Model.Feed;
import com.example.tp3.R;

import java.util.List;

public class DetailFeedAdapter extends RecyclerView.Adapter<DetailFeedAdapter.ViewHolder> {

    private Context context;
    private List<Feed> feedList;

    public DetailFeedAdapter(Context context, List<Feed> feedList) {
        this.context = context;
        this.feedList = feedList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Feed feed = feedList.get(position);

        holder.profileImage.setImageResource(feed.getUserProfileImage());
        // Cek apakah feed memiliki imageUri
        if (feed.getImageUri() != null) {
            holder.feedImage.setImageURI(feed.getImageUri());
        } else {
            holder.feedImage.setImageResource(feed.getFeedImage());
        }

        holder.captionFeed.setText(feed.getCaption());
        holder.username.setText(feed.getUsername());
        holder.usernameCapt.setText(feed.getUsername());

    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView feedImage,profileImage;
        TextView captionFeed, username, usernameCapt;

        public ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.iv_profileFeed);
            feedImage = itemView.findViewById(R.id.iv_feedImage);
            captionFeed = itemView.findViewById(R.id.tv_caption);
            username = itemView.findViewById(R.id.tv_usernameFeed);
            usernameCapt = itemView.findViewById(R.id.tv_captionUsername);
        }
    }
}
