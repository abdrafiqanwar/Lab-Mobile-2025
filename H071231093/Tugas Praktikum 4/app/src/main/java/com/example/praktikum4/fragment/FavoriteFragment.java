package com.example.praktikum4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum4.R;
import com.example.praktikum4.adapter.BookAdapter;
import com.example.praktikum4.data.BookData;
import com.example.praktikum4.jclass.Book;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_favorite_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<Book> favoriteBooks = new ArrayList<>();
        for (Book book : BookData.getBooks()) {
            if (book.isFavorite()) {
                favoriteBooks.add(book);
            }
        }

        BookAdapter adapter = new BookAdapter(getContext(), favoriteBooks, book -> {
            DetailBookFragment detailFragment = new DetailBookFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedBook", book);
            detailFragment.setArguments(bundle);

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}