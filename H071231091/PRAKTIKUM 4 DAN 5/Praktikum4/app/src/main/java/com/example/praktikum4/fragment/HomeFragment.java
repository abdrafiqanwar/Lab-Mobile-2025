package com.example.praktikum4.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private ProgressBar progressBar;
    private TextView noResultTextView;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_books);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Ambil data buku
        List<Book> books = BookData.getBooks();

        noResultTextView = view.findViewById(R.id.text_view_no_result);

        // Inisialisasi adapter
        adapter = new BookAdapter(getContext(), books, book -> {
            // Navigasi ke DetailBookFragment saat buku diklik
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

        // KOSNONG
        adapter.setFilterListener(resultCount -> {
            noResultTextView.setVisibility(resultCount == 0 ? View.VISIBLE : View.GONE);
        });

        recyclerView.setAdapter(adapter);

        // Inisialisasi SearchView dan ProgressBar
        SearchView searchView = view.findViewById(R.id.search_view_books);
        progressBar = view.findViewById(R.id.progress_bar);

        // Atur tampilan awal SearchView
        searchView.setQueryHint("Search books");
        searchView.setIconifiedByDefault(false);

        // Listener saat teks pencarian diketik atau submit
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearchInBackground(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearchInBackground(newText);
                return true;
            }
        });

        return view;
    }

    // Proses pencarian dijalankan di thread terpisah agar UI tidak lag
    private void performSearchInBackground(String query) {
        progressBar.setVisibility(View.VISIBLE); // Tampilkan loading

        new Thread(() -> {
            try {
                Thread.sleep(300); // Simulasi delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Kembali ke UI thread untuk update tampilan
            requireActivity().runOnUiThread(() -> {
                adapter.getFilter().filter(query); // Filter data berdasarkan query
                progressBar.setVisibility(View.GONE);
            });
        }).start();
    }
}
