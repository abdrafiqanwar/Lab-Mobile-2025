package com.example.instagram.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.DetailActivity;
import com.example.instagram.ProfileActivity;
import com.example.instagram.R;
import com.example.instagram.model.FeedModel;
import com.example.instagram.model.PhotoModel;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private final Context context;
    private final List<FeedModel> feedList;

    public ProfileAdapter(Context context, List<FeedModel> feedList) {
        this.context = context;
        this.feedList = feedList;
    }


    @NonNull
    @Override
    public ProfileAdapter.ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ProfileViewHolder holder, int position) {
        FeedModel feed = feedList.get(position);
        if (feed.getPhotoList() != null && !feed.getPhotoList().isEmpty()) {

            PhotoModel photo = feed.getPhotoList().get(0);

            if (photo.getImageUrl() != null) {
                Glide.with(context)
                        .load(photo.getImageUrl())
                        .into(holder.imagePost);
            }
            else if (photo.getUri() != null) {
                holder.imagePost.setImageURI(photo.getUri());
            } else {
                holder.imagePost.setImageResource(photo.getImageRes());
            }

            holder.imagePost.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("feed", feed);
                context.startActivity(intent);
            });
        }


    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePost;
        TextView bioProfile;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.imagePost);
            bioProfile = itemView.findViewById(R.id.bioProfile);
        }
    }
}
