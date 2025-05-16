package com.example.praktikum5.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView; // <-- Tambahkan import ini

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum5.R;
import com.example.praktikum5.adapter.BookAdapter;
import com.example.praktikum5.data.BookData;
import com.example.praktikum5.jclass.Book;

import java.util.List;

public class HomeFragment extends Fragment {

    private BookAdapter adapter;
    private View loadingContainer;
    private RecyclerView recyclerView; // <-- Jadikan variabel instance
    private TextView emptyViewHome;   // <-- Tambahkan variabel untuk empty view

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_books);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        emptyViewHome = view.findViewById(R.id.empty_view_home); // <-- Inisialisasi empty view

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

        checkIfListEmpty(); // <-- Panggil untuk pengecekan awal

        SearchView searchView = view.findViewById(R.id.search_view_books);
        searchView.setQueryHint("Search books");
        searchView.setIconifiedByDefault(false);

        loadingContainer = view.findViewById(R.id.loading_container);
        loadingContainer.setVisibility(View.GONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
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
                // Setelah genre diubah, jalankan kembali pencarian dengan query yang ada
                // atau reset filter pencarian jika diperlukan.
                // Di sini kita akan menjalankan ulang pencarian dengan query saat ini.
                performSearch(searchView.getQuery().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adapter.setSelectedGenre("All");
                performSearch(searchView.getQuery().toString());
            }
        });

        return view;
    }

    private void performSearch(String query) {
        loadingContainer.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE); // Sembunyikan RecyclerView selama loading
        emptyViewHome.setVisibility(View.GONE);  // Sembunyikan empty view selama loading

        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulasi delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            requireActivity().runOnUiThread(() -> {
                adapter.getFilter().filter(query);
                // Penting: Filter di adapter berjalan secara asinkron.
                // Kita perlu menunggu hasilnya sebelum memeriksa itemCount.
                // Cara sederhana adalah dengan postDelayed kecil atau, lebih baik,
                // menggunakan FilterListener jika adapter Anda mendukungnya.
                // Untuk contoh ini, kita akan asumsikan filter selesai cukup cepat
                // atau kita bisa menambahkan delay kecil.
                // Handler().postDelayed(() -> {
                //    loadingContainer.setVisibility(View.GONE);
                //    checkIfListEmpty();
                // }, 50); // Sedikit delay untuk memastikan filter selesai
                // Lebih baik jika BookAdapter memiliki callback setelah filter selesai.
                // Untuk sekarang, kita panggil langsung setelah filter.
                loadingContainer.setVisibility(View.GONE);
                checkIfListEmpty();
            });
        }).start();
    }

    private void checkIfListEmpty() {
        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyViewHome.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyViewHome.setVisibility(View.GONE);
        }
    }
}