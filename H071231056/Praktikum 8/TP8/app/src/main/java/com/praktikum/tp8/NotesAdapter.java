package com.praktikum.tp8;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<Note> notesList;
    private Context context;
    private DatabaseHelper databaseHelper;
    private OnNoteActionListener listener;

    public interface OnNoteActionListener {
        void onNoteDeleted();
    }

    public NotesAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.databaseHelper = new DatabaseHelper(context);
        if (context instanceof OnNoteActionListener) {
            this.listener = (OnNoteActionListener) context;
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notes, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.descriptionTextView.setText(note.getDescription());

        // Show appropriate timestamp based on whether note was updated
        if (note.getCreatedAt().equals(note.getUpdatedAt())) {
            holder.timestampTextView.setText("Created at " + note.getCreatedAt());
        } else {
            holder.timestampTextView.setText("Updated at " + note.getUpdatedAt());
        }

        // Single click to edit
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditNoteActivity.class);
            intent.putExtra("note_id", note.getId());
            intent.putExtra("note_title", note.getTitle());
            intent.putExtra("note_description", note.getDescription());
            context.startActivity(intent);
        });

        // Long click for context menu
        holder.itemView.setOnLongClickListener(v -> {
            showContextMenu(note, position);
            return true;
        });
    }

    private void showContextMenu(Note note, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(note.getTitle())
                .setItems(new String[]{"Edit", "Delete"}, (dialog, which) -> {
                    switch (which) {
                        case 0: // Edit
                            Intent intent = new Intent(context, AddEditNoteActivity.class);
                            intent.putExtra("note_id", note.getId());
                            intent.putExtra("note_title", note.getTitle());
                            intent.putExtra("note_description", note.getDescription());
                            context.startActivity(intent);
                            break;
                        case 1: // Delete
                            showDeleteConfirmation(note, position);
                            break;
                    }
                });
        builder.show();
    }

    private void showDeleteConfirmation(Note note, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Hapus")
                .setMessage("Apakah anda yakin ingin menghapus note \"" + note.getTitle() + "\"?")
                .setPositiveButton("YA", (dialog, which) -> {
                    databaseHelper.deleteNote(note.getId());
                    notesList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, notesList.size());
                    Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_SHORT).show();

                    if (listener != null) {
                        listener.onNoteDeleted();
                    }
                })
                .setNegativeButton("TIDAK", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void updateNotes(List<Note> newNotesList) {
        this.notesList = newNotesList;
        notifyDataSetChanged();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView timestampTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_title);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
            timestampTextView = itemView.findViewById(R.id.tv_timestamp);
        }
    }
}