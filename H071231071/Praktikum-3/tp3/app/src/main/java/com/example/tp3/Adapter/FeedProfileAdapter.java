package com.example.tp3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.FeedDetailActivity;
import com.example.tp3.Model.Feed;
import com.example.tp3.R;

import java.util.List;

public class FeedProfileAdapter extends RecyclerView.Adapter<FeedProfileAdapter.FeedProfileViewHolder> {

    private Context context;
    private List<Feed> feedList;

    public FeedProfileAdapter(Context context, List<Feed> feedList) {
        this.context = context;
        this.feedList = feedList;
    }

    @NonNull
    @Override
    public FeedProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feed_grid_item, parent, false);
        return new FeedProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedProfileViewHolder holder, int position) {
        Feed feed = feedList.get(position);

        // Cek apakah feed memiliki imageUri
        if (feed.getImageUri() != null) {
            holder.feedImage.setImageURI(feed.getImageUri());
        } else {
            holder.feedImage.setImageResource(feed.getFeedImage());
        }

        // Menangani klik item untuk detail feed
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FeedDetailActivity.class);
            intent.putExtra("SELECTED_POSITION", holder.getAdapterPosition());  // Pastikan posisi yang valid
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return feedList.size();
    }

    class FeedProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView feedImage;

        public FeedProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            feedImage = itemView.findViewById(R.id.iv_feedImageGrid);

        }
    }
}
