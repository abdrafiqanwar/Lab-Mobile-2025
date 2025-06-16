package com.projeku.tuprak8;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private Context context;
    private OnItemClickListener listener;

    // Interface untuk menangani klik pada item RecyclerView
    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public NoteAdapter(Context context, List<Note> noteList, OnItemClickListener listener) {
        this.context = context;
        this.noteList = noteList != null ? noteList : new ArrayList<>(); // Pastikan noteList tidak null
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Membuat view baru dari layout item_note.xml
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // Mengikat data dari objek Note ke ViewHolder
        Note currentNote = noteList.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewContent.setText(currentNote.getContent());

        // Menentukan apakah akan menampilkan "Created at" atau "Updated at"
        // Logika sederhana: jika waktu dibuat dan diupdate berbeda lebih dari beberapa detik, anggap sudah diupdate.
        // Anda bisa menyesuaikan logika ini.
        String createdAtStr = currentNote.getCreatedAt();
        String updatedAtStr = currentNote.getUpdatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

        try {
            Date createdAtDate = sdf.parse(createdAtStr);
            Date updatedAtDate = sdf.parse(updatedAtStr);

            if (createdAtDate != null && updatedAtDate != null) {
                // Hitung selisih waktu dalam detik
                long diffInMillis = Math.abs(updatedAtDate.getTime() - createdAtDate.getTime());
                long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

                if (diffInSeconds > 5) { // Anggap diupdate jika selisih lebih dari 5 detik
                    holder.textViewTimestamp.setText("Updated at " + updatedAtStr);
                } else {
                    holder.textViewTimestamp.setText("Created at " + createdAtStr);
                }
            } else {
                // Fallback jika ada masalah parsing tanggal
                holder.textViewTimestamp.setText("Created at " + createdAtStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.textViewTimestamp.setText("Created at " + createdAtStr); // Fallback
        }


        // Menetapkan click listener untuk item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentNote);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Mengembalikan jumlah item dalam list
        return noteList.size();
    }

    // Metode untuk memperbarui data di adapter dan memberi tahu RecyclerView
    public void setNotes(List<Note> notes) {
        this.noteList = notes != null ? notes : new ArrayList<>();
        notifyDataSetChanged(); // Memberi tahu adapter bahwa data telah berubah
    }

    // ViewHolder untuk menampung view dari setiap item catatan
    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewContent;
        TextView textViewTimestamp;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
        }
    }
}
