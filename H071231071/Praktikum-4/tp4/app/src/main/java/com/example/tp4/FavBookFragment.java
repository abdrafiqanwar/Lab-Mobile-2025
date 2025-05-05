package com.example.tp4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp4.Adapter.BookAdapter;
import com.example.tp4.Model.FavoriteStore;

import androidx.annotation.Nullable;


public class FavBookFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_book, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_fav_books);
        adapter = new BookAdapter(getContext(), FavoriteStore.favoriteBooks, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); // Refresh saat kembali
    }
}
