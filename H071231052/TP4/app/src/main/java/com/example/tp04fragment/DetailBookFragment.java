package com.example.tp04fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp04fragment.Models.Book;
import com.example.tp04fragment.Repository.BookRepository;
import com.example.tp04fragment.databinding.FragmentDetailBookBinding;
import com.example.tp04fragment.databinding.FragmentHomeBinding;

public class DetailBookFragment extends Fragment {
    private FragmentDetailBookBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBookBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((MainActivity) requireActivity()).hideBottomNav();

        binding.back.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        Book book = getArguments().getParcelable("book");
        if (book != null) {
            Uri coverUri = book.getCoverUri();
            int coverDrawable = book.getCover();

            if (coverUri != null) {
                try {
                    binding.cover.setImageURI(coverUri);
                    if (binding.cover.getDrawable() == null) {
                        binding.cover.setImageResource(coverDrawable);
                    }
                } catch (Exception e) {
                    binding.cover.setImageResource(coverDrawable);
                }
            } else {
                binding.cover.setImageResource(coverDrawable);
            }

            // Set detail lainnya
            binding.judul.setText(book.getJudul());
            binding.penulis.setText(book.getPenulis());
            binding.sinopsis.setText(book.getSinopsis());
            binding.halaman.setText(String.valueOf(book.getHalaman()));
            binding.tahun.setText(String.valueOf(book.getTahun()));
            binding.genre.setText(book.getGenre());
            binding.blurb.setText(book.getBlurb());
            updateLikeIcon(book.isFavorite());
            binding.rating.setText(String.valueOf(book.getRating()));

            binding.favorites.setOnClickListener(v -> {
                book.setFavorite(!book.isFavorite());
                updateLikeIcon(book.isFavorite());

                if (book.isFavorite()) {
                    BookRepository.addToFavorites(book);
                } else {
                    BookRepository.removeFromFavorites(book);
                }
            });
        }

        return view;
    }

    private void updateLikeIcon(boolean isFavorited) {
        if (isFavorited) {
            binding.favorites.setImageResource(R.drawable.favorite_on);
        } else {
            binding.favorites.setImageResource(R.drawable.favorite_off);
        }
    }
}