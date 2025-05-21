package com.example.praktikum_4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private EditText searchInput;
    private Button searchButton;
    private ProgressBar progressBar;

    private List<Book> displayedBooks;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.book_list);
        searchInput = view.findViewById(R.id.search_input);
        searchButton = view.findViewById(R.id.btn_search);
        progressBar = view.findViewById(R.id.progress_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        displayedBooks = new ArrayList<>(BookData.books);
        adapter = new BookAdapter(displayedBooks, book -> openBookDetail(book));
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> searchBooks());

        return view;
    }

    private void searchBooks() {
        String query = searchInput.getText().toString().toLowerCase();

        // Tampilkan loading
        progressBar.setVisibility(View.VISIBLE);
        searchButton.setEnabled(false);

        executor.execute(() -> {
            try {
                // Simulasi delay selama 1 detik agar loading terlihat
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> filtered = new ArrayList<>();
            for (Book b : BookData.books) {
                if (b.title.toLowerCase().contains(query) || b.author.toLowerCase().contains(query)) {
                    filtered.add(b);
                }
            }

            mainHandler.post(() -> {
                displayedBooks.clear();
                displayedBooks.addAll(filtered);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                searchButton.setEnabled(true);
            });
        });
    }

    private void openBookDetail(Book book) {
        Bundle bundle = new Bundle();
        bundle.putString("title", book.title);
        bundle.putString("author", book.author);
        bundle.putString("year", book.year);
        bundle.putString("blurb", book.blurb);
        bundle.putInt("coverResId", book.coverResId);
        bundle.putBoolean("isLiked", book.isLiked);

        BookDetailFragment detailFragment = new BookDetailFragment();
        detailFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        displayedBooks.clear();
        displayedBooks.addAll(BookData.books);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
