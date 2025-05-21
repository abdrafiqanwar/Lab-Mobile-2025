package com.example.praktikum_4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;

public class BookDetailFragment extends Fragment {

    private Book selectedBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        // Ambil data dari arguments
        Bundle args = getArguments();
        if (args == null) return view;

        String titleStr = args.getString("title");
        String author = args.getString("author");
        String year = args.getString("year");
        String blurbStr = args.getString("blurb");
        int coverResId = args.getInt("coverResId");

        // Temukan buku berdasarkan title
        for (Book book : BookData.books) {
            if (book.title.equals(titleStr)) {
                selectedBook = book;
                break;
            }
        }

        // Bind view
        ImageView cover = view.findViewById(R.id.detail_cover);
        TextView title = view.findViewById(R.id.detail_title);
        TextView authorYear = view.findViewById(R.id.detail_author_year);
        TextView blurb = view.findViewById(R.id.detail_blurb);
        Button favButton = view.findViewById(R.id.btn_favorite);

        // Isi data
        if (selectedBook != null && selectedBook.coverBitmap != null) {
            cover.setImageBitmap(selectedBook.coverBitmap);
        } else {
            cover.setImageResource(selectedBook.coverResId);
        }
        title.setText(titleStr);
        authorYear.setText(author + " - " + year);
        blurb.setText(blurbStr);

        // Atur teks tombol berdasarkan status like
        updateFavoriteButtonText(favButton);

        // Handle klik tombol favorit
        favButton.setOnClickListener(v -> {
            if (selectedBook != null) {
                selectedBook.isLiked = !selectedBook.isLiked;
                if (selectedBook.isLiked) {
                    if (!BookData.favoriteBooks.contains(selectedBook)) {
                        BookData.favoriteBooks.add(selectedBook);
                    }
                } else {
                    BookData.favoriteBooks.remove(selectedBook);
                }
                updateFavoriteButtonText(favButton);
            }
        });
        return view;
    }

    private void updateFavoriteButtonText(Button button) {
        if (selectedBook != null && selectedBook.isLiked) {
            button.setText("Hapus dari Favorit");
        } else {
            button.setText("Tambah ke Favorit");
        }
    }
}
