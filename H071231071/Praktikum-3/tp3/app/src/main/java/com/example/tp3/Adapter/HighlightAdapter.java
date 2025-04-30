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


import com.example.tp3.HighlightDetailActivity;
import com.example.tp3.Model.Highlight;
import com.example.tp3.R;

import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.HighlightViewHolder> {

    private Context context;
    private List<Highlight> highlights;

    public HighlightAdapter(Context context, List<Highlight> highlights) {
        this.context = context;
        this.highlights = highlights;
    }

    @NonNull
    @Override
    public HighlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.highlight_item, parent, false);
        return new HighlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightViewHolder holder, int position) {
        Highlight highlight = highlights.get(position);
        holder.highlightCover.setImageResource(highlight.getCoverImage());
        holder.highlightTitle.setText(highlight.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HighlightDetailActivity.class);
            intent.putExtra("HIGHLIGHT_TITLE", highlight.getTitle());
            intent.putExtra("HIGHLIGHT_IMAGE", highlight.getCoverImage());
            intent.putExtra("HIGHLIGHT_DETAIL_STORY", highlight.getDetailStory());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return highlights.size();
    }

    class HighlightViewHolder extends RecyclerView.ViewHolder {
        ImageView highlightCover;
        TextView highlightTitle;

        public HighlightViewHolder(@NonNull View itemView) {
            super(itemView);
            highlightCover = itemView.findViewById(R.id.iv_highlightImage);
            highlightTitle = itemView.findViewById(R.id.tv_highlightTitle);
        }
    }
}