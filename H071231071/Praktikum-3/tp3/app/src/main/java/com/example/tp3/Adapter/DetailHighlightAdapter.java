package com.example.tp3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tp3.Model.Highlight;
import com.example.tp3.R;

import java.util.List;

public class DetailHighlightAdapter extends RecyclerView.Adapter<DetailHighlightAdapter.HighlightViewHolder> {

    private Context context;
    private List<Highlight> detailHighlight;

    public DetailHighlightAdapter(Context context, List<Highlight> detailHighlight) {
        this.context = context;
        this.detailHighlight = detailHighlight;
    }

    @NonNull
    @Override
    public HighlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_highlight_item, parent, false);
        return new HighlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightViewHolder holder, int position) {
        Highlight highlight = detailHighlight.get(position);
        holder.highlightCover.setImageResource(highlight.getCoverImage());
        holder.highlightTitle.setText(highlight.getTitle());
        holder.detailHighlight.setImageResource(highlight.getDetailStory());
    }

    @Override
    public int getItemCount() {
        return detailHighlight.size();
    }

    class HighlightViewHolder extends RecyclerView.ViewHolder {
        ImageView highlightCover;
        TextView highlightTitle;

        ImageView detailHighlight;

        public HighlightViewHolder(@NonNull View itemView) {
            super(itemView);
            highlightCover = itemView.findViewById(R.id.iv_highlightImage);
            highlightTitle = itemView.findViewById(R.id.tv_highlightTitle);
            detailHighlight = itemView.findViewById(R.id.iv_story);
        }
    }
}