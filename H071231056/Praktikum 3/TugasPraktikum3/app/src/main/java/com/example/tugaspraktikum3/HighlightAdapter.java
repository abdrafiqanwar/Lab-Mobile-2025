package com.example.tugaspraktikum3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {

    private List<User.Story> highlights;

    public HighlightAdapter(List<User.Story> highlights) {
        this.highlights = highlights;
    }

    @NonNull
    @Override
    public HighlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_highlight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightAdapter.ViewHolder holder, int position) {
        User.Story story = highlights.get(position);
        holder.setData(story);
    }

    @Override
    public int getItemCount() {
        return highlights.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageHighlight;
        private TextView textTitle;
        private User.Story currentStory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHighlight = itemView.findViewById(R.id.imageHighlight);
            textTitle = itemView.findViewById(R.id.textTitle);

            imageHighlight.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && currentStory != null) {
                    openProfileActivity(currentStory.getImageResId());
                }
            });
        }

        private void openProfileActivity(int imageResId) {
            Intent intent = new Intent(itemView.getContext(), DetailHighlightActivity.class);
            intent.putExtra("IMAGE_RES_ID", imageResId);
            itemView.getContext().startActivity(intent);
        }


        public void setData(User.Story story) {
            currentStory = story;
            textTitle.setText(story.getTitle());
            imageHighlight.setImageResource(story.getImageResId());
        }
    }

}
