package com.example.praktikum3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum3.R;
import com.example.praktikum3.jclass.Story;

import java.util.ArrayList;

// Adapter untuk menampilkan daftar story highlight
public class StoryHighlightAdapter extends RecyclerView.Adapter<StoryHighlightAdapter.ViewHolder> {

    private ArrayList<Story> storyList;
    private OnStoryClickListener storyClickListener;

    // Interface listener klik pada satu story
    public interface OnStoryClickListener {
        void onStoryClick(Story story);
    }

    // Konstruktor adapter: inisialisasi daftar story dan listener
    public StoryHighlightAdapter(ArrayList<Story> storyList, OnStoryClickListener listener) {
        this.storyList = storyList;
        this.storyClickListener = listener;
    }

    @NonNull
    @Override
    public StoryHighlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_highlight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHighlightAdapter.ViewHolder holder, int position) {
        Story story = storyList.get(position); // Ambil data story sesuai posisi

        // Tampilkan gambar dengan Glide, crop jadi lingkaran dan ukurannya 120x120
        Glide.with(holder.itemView.getContext())
                .load(story.getImageRes()) // Ambil gambar dari resource ID
                .override(120, 120) // Atur ukuran gambar
                .circleCrop() // Potong gambar jadi lingkaran
                .into(holder.imageView); // Masukkan ke dalam imageView

        // Tampilkan judul/hightlight name
        holder.textView.setText(story.getTitle());

        // Set listener jika item diklik
        holder.itemView.setOnClickListener(v -> storyClickListener.onStoryClick(story));
    }

    // Mengembalikan jumlah item dalam list
    @Override
    public int getItemCount() {
        return storyList.size();
    }

    // ViewHolder sebagai representasi item tunggal
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // Untuk menampilkan gambar story
        TextView textView;   // Untuk menampilkan nama/label story

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_story); // Hubungkan dengan ImageView di layout
            textView = itemView.findViewById(R.id.tv_story_name); // Hubungkan dengan TextView di layout
        }
    }
}
