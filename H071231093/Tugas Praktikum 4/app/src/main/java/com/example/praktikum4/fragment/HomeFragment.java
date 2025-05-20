package com.example.praktikum4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum4.R;
import com.example.praktikum4.adapter.BookAdapter;
import com.example.praktikum4.data.BookData;
import com.example.praktikum4.jclass.Book;

import java.util.List;

public class HomeFragment extends Fragment {

    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_books);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<Book> books = BookData.getBooks();
        adapter = new BookAdapter(getContext(), books, book -> {
            DetailBookFragment detailFragment = new DetailBookFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedBook", book);
            detailFragment.setArguments(bundle);

            FragmentTransaction ft = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            ft.replace(R.id.fragment_container, detailFragment);
            ft.addToBackStack(null);
            ft.commit();
        });
        recyclerView.setAdapter(adapter);

        SearchView searchView = view.findViewById(R.id.search_view_books);
        searchView.setQueryHint("Search books");
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        Spinner genreSpinner = view.findViewById(R.id.spinner_genre_filter);
        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.genre_array_filter, android.R.layout.simple_spinner_item);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGenre = parent.getItemAtPosition(position).toString();
                adapter.setSelectedGenre(selectedGenre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adapter.setSelectedGenre("All");
            }
        });

        return view;
    }
}
