package com.example.tp7_h071231092.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp7_h071231092.R;
import com.example.tp7_h071231092.ui.FormActivity;
import com.example.tp7_h071231092.data.Notes;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private final ArrayList<Notes> notesList = new ArrayList<>();
    private final Activity activity;

    public NotesAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setNotes(ArrayList<Notes> notes) {
        this.notesList.clear();
        if (notes.size() > 0) {
            this.notesList.addAll(notes);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bind(notesList.get(position));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        final TextView tvJudul, tvDeskripsi, tvCreatedAt;
        final CardView cardView;

        NotesViewHolder(View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            cardView = itemView.findViewById(R.id.card_view);
            tvCreatedAt = itemView.findViewById(R.id.tv_created_at);
        }

        void bind(Notes note) {
            tvJudul.setText(note.getJudul());
            tvDeskripsi.setText(note.getDeskripsi());

            // Display the formatted created_at value
            tvCreatedAt.setText(note.getCreatedAt());

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(activity, FormActivity.class);
                intent.putExtra(FormActivity.EXTRA_NOTE, note);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE);
            });
        }
    }
}