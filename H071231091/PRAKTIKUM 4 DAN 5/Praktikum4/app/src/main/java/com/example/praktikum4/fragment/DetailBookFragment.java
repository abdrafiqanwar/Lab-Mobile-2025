package com.example.praktikum4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.praktikum4.R;
import com.example.praktikum4.jclass.Book;

public class DetailBookFragment extends Fragment {

    private Book selectedBook; // Menyimpan data buku yang akan ditampilkan

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout untuk fragment ini
        View view = inflater.inflate(R.layout.fragment_detail_book, container, false);

        // Inisialisasi semua komponen UI
        ImageView coverImageView = view.findViewById(R.id.image_view_cover);
        TextView titleTextView = view.findViewById(R.id.text_view_title);
        TextView authorTextView = view.findViewById(R.id.text_view_author);
        TextView yearTextView = view.findViewById(R.id.text_view_year);
        TextView synopsisTextView = view.findViewById(R.id.text_view_synopsis);
        TextView genreTextView = view.findViewById(R.id.text_view_genre);
        TextView ratingTextView = view.findViewById(R.id.text_view_rating);
        Button addToFavoriteButton = view.findViewById(R.id.button_add_to_favorite);


        if (getArguments() != null) {
            selectedBook = (Book) getArguments().getSerializable("selectedBook");

            if (selectedBook != null) {
                // Menampilkan data buku ke dalam view
                Glide.with(this).load(selectedBook.getCoverImageUri()).into(coverImageView);
                titleTextView.setText(selectedBook.getTitle());
                authorTextView.setText(selectedBook.getAuthor());
                yearTextView.setText(String.valueOf(selectedBook.getYear()));
                synopsisTextView.setText(selectedBook.getSynopsis());
                genreTextView.setText(selectedBook.getGenre());
                ratingTextView.setText(String.valueOf(selectedBook.getRating()));

                updateFavoriteButton(addToFavoriteButton, selectedBook.isFavorite());

                // Fungsi ketika tombol favorite diklik
                addToFavoriteButton.setOnClickListener(v -> {
                    selectedBook.setFavorite(!selectedBook.isFavorite());
                    // Update tampilan tombol
                    updateFavoriteButton(addToFavoriteButton, selectedBook.isFavorite());
                    // Tampilkan pesan toast
                    Toast.makeText(getContext(),
                            selectedBook.isFavorite() ? "Ditambahkan ke Favorit" : "Dihapus dari Favorit",
                            Toast.LENGTH_SHORT).show();
                });
            }
        }

        return view;
    }

    // Method untuk mengupdate teks tombol favorite
    private void updateFavoriteButton(Button button, boolean isFavorite) {
        button.setText(isFavorite ? "Hapus dari Favorit" : "Tambahkan ke Favorit");
    }
}