package com.example.tp3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.Model.StoryModel;
import com.example.tp3.Model.User;
import com.example.tp3.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ADD = 0;
    private static final int VIEW_TYPE_STORY = 1;

    private Context context;
    private List<User> userList;
    private FeedHomeAdapter.OnUserClickListener listener;

    public StoryAdapter(Context context, List<User> userList, FeedHomeAdapter.OnUserClickListener listener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_ADD : VIEW_TYPE_STORY;
    }

    @Override
    public int getItemCount() {
        return userList.size() + 1; // +1 untuk story add
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ADD) {
            View view = LayoutInflater.from(context).inflate(R.layout.story_item_add, parent, false);
            return new AddStoryViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.story_item, parent, false);
            return new StoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_STORY) {
            User user = userList.get(position - 1); // karena index 0 untuk add
            ((StoryViewHolder) holder).bind(user);
        }
    }

    class AddStoryViewHolder extends RecyclerView.ViewHolder {
        public AddStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            // tambahkan event klik kalau perlu
        }
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStory;
        TextView tvUsername;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStory = itemView.findViewById(R.id.iv_profileStory);
            tvUsername = itemView.findViewById(R.id.tv_usernameStory);
        }

        public void bind(User user) {
            tvUsername.setText(user.getUsername());
            ivStory.setImageResource(user.getProfileImage());
            itemView.setOnClickListener(v -> listener.onUserClick(user));
        }
    }
}
