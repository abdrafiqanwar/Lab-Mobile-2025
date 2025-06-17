package com.example.praktikum_8.note;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum_8.R;
import com.example.praktikum_8.activity.FormActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final ArrayList<Note> notes = new ArrayList<>();
    private final Activity activity;

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes.clear();
        if (notes != null && !notes.isEmpty()) {
            this.notes.addAll(notes);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.praktikum_8.R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvJudul, tvDeskripsi, tvTime;
        final MaterialCardView cardLayout;

        NoteViewHolder(View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_item_judul);
            tvDeskripsi = itemView.findViewById(R.id.tv_item_deskripsi);
            tvTime = itemView.findViewById(R.id.tv_item_timestamp);
            cardLayout = itemView.findViewById(R.id.card_view);
        }

        void bind(Note note) {
            tvJudul.setText(note.getJudul());
            tvDeskripsi.setText(note.getDeskripsi());

            String timestampText;
            if (note.getUpdatedAt() != null && !note.getUpdatedAt().isEmpty()) {
                timestampText = "Diperbarui pada: " + note.getUpdatedAt();
            } else if (note.getCreatedAt() != null && !note.getCreatedAt().isEmpty()) {
                timestampText = "Dibuat pada: " + note.getCreatedAt();
            } else {
                timestampText = "Tanggal tidak tersedia";
            }
            tvTime.setText(timestampText);

            cardLayout.setOnClickListener(v -> {
                Intent intent = new Intent(activity, FormActivity.class);
                intent.putExtra(FormActivity.EXTRA_NOTE, note);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE);
            });
        }
    }
}