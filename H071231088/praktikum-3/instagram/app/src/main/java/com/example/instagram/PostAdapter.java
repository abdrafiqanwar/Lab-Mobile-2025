package com.example.instagram;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;

    public PostAdapter(ArrayList<Post> posts){
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.iv_profile.setImageResource(post.getImage());
        holder.name.setText(post.getName());
        holder.time.setText(post.getTime());
        holder.caption.setText(post.getCaption());
        holder.likes.setText(String.valueOf(post.getLikes()));
        holder.comments.setText(String.valueOf(post.getComments()));
        holder.shares.setText(String.valueOf(post.getShares()));

        // Ketika foto profil ditekan
        holder.iv_profile.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailAccountActivity.class);
            intent.putExtra("username", post.getName());  // Mengirimkan data username
            holder.itemView.getContext().startActivity(intent);
        });

        // Ketika nama ditekan
        holder.name.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailAccountActivity.class);
            intent.putExtra("username", post.getName());  // Mengirimkan data username
            holder.itemView.getContext().startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView iv_profile;
        private TextView name, time, caption, likes, comments, shares;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            caption = itemView.findViewById(R.id.caption);
            likes = itemView.findViewById(R.id.likes);
            comments = itemView.findViewById(R.id.comments);
            shares = itemView.findViewById(R.id.shares);
        }
    }
}
