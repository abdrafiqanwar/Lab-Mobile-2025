package com.example.tp3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.Model.Feed;
import com.example.tp3.Model.User;
import com.example.tp3.R;

import java.util.List;
public class FeedHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_STORY_CONTAINER = 0;
    private static final int VIEW_TYPE_FEED = 1;

    private Context context;
    private List<Feed> feedList;
    private List<User> storyUserList; // Untuk adapter story
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public FeedHomeAdapter(Context context, List<Feed> feedList, List<User> storyUserList, OnUserClickListener listener) {
        this.context = context;
        this.feedList = feedList;
        this.storyUserList = storyUserList;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_STORY_CONTAINER : VIEW_TYPE_FEED;
    }

    @Override
    public int getItemCount() {
        return feedList.size() + 1; // +1 untuk story container di posisi 0
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_STORY_CONTAINER) {
            View view = LayoutInflater.from(context).inflate(R.layout.story_container_for_home_item, parent, false);
            return new StoryContainerViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.feed_item, parent, false);
            return new FeedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_STORY_CONTAINER) {
            ((StoryContainerViewHolder) holder).bindStories();
        } else {
            Feed feed = feedList.get(position - 1);
            ((FeedViewHolder) holder).setData(feed);
        }
    }

    class StoryContainerViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvStory;

        public StoryContainerViewHolder(@NonNull View itemView) {
            super(itemView);
            rvStory = itemView.findViewById(R.id.rv_story);
            rvStory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }

        public void bindStories() {
            StoryAdapter storyAdapter = new StoryAdapter(context, storyUserList, listener);
            rvStory.setAdapter(storyAdapter);
        }
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage, feedImage;
        TextView username, caption, usernameCaption;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.iv_profileFeed);
            feedImage = itemView.findViewById(R.id.iv_feedImage);
            username = itemView.findViewById(R.id.tv_usernameFeed);
            caption = itemView.findViewById(R.id.tv_caption);
            usernameCaption = itemView.findViewById(R.id.tv_captionUsername);

        }

        public void setData(Feed feed) {
            username.setText(feed.getUser().getUsername());
            profileImage.setImageResource(feed.getUser().getProfileImage());

            // Cek apakah feed memiliki imageUri
            if (feed.getImageUri() != null) {
                feedImage.setImageURI(feed.getImageUri());
            } else {
                feedImage.setImageResource(feed.getFeedImage());
            }
            caption.setText(feed.getCaption());
            usernameCaption.setText(feed.getUser().getUsername());

            profileImage.setOnClickListener(v -> listener.onUserClick(feed.getUser()));
            username.setOnClickListener(v -> listener.onUserClick(feed.getUser()));
            usernameCaption.setOnClickListener(v -> listener.onUserClick(feed.getUser()));

        }
    }
}
