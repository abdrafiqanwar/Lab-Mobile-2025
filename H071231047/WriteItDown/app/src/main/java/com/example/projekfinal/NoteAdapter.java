package com.example.projekfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final Context context;
    private List<Note> noteList;
    private final OnItemClickListener listener;
    private OnFavoriteToggleListener onFavoriteToggleListener;
    private DatabaseHelper dbHelper;

    public NoteAdapter(Context context, List<Note> noteList, OnItemClickListener listener) {
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
        this.dbHelper = new DatabaseHelper(context);
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    // Add interface for favorite toggle events
    public interface OnFavoriteToggleListener {
        void onFavoriteToggled(long noteId);
    }

    // Setter for the favorite toggle listener
    public void setOnFavoriteToggleListener(OnFavoriteToggleListener listener) {
        this.onFavoriteToggleListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.contentPreviewTextView.setText(note.getContent());
        holder.timestampTextView.setText(note.getUpdatedAt());

        // Set category text
        holder.categoryTextView.setText(note.getCategory());

        // Set favorite icon based on note status
        if (note.isFavorite()) {
            holder.favoriteButton.setImageResource(R.drawable.ic_star_filled);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_star_outline);
        }

        // Handle favorite button click
        holder.favoriteButton.setOnClickListener(v -> {
            dbHelper.toggleFavorite(note.getId());
            note.setFavorite(!note.isFavorite());
            notifyItemChanged(position);

            // Notify listener if provided
            if (onFavoriteToggleListener != null) {
                onFavoriteToggleListener.onFavoriteToggled(note.getId());
            }
        });

        // Handle note item click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(List<Note> notes) {
        this.noteList = notes;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentPreviewTextView, timestampTextView, categoryTextView;
        ImageButton favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            contentPreviewTextView = itemView.findViewById(R.id.textViewContentPreview);
            timestampTextView = itemView.findViewById(R.id.textViewTimestamp);
            favoriteButton = itemView.findViewById(R.id.buttonFavorite);
            categoryTextView = itemView.findViewById(R.id.textViewCategory);
        }
    }
}