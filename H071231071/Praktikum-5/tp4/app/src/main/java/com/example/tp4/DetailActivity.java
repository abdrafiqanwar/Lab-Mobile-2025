package com.example.tp4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4.Adapter.DetailAdapter;
import com.example.tp4.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerDetail;
    private DetailAdapter adapter;
    private List<Book> bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerDetail = findViewById(R.id.recycler_detail);
        recyclerDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Ambil data dari intent (jika hanya 1 buku)
        Book book = getIntent().getParcelableExtra("book");

        // Simpan ke list untuk adapter
        bookList = new ArrayList<>();
        if (book != null) {
            bookList.add(book);
        }

        adapter = new DetailAdapter(this, bookList);
        adapter.setOnBackClickListener(() -> finish());
        recyclerDetail.setAdapter(adapter);


    }
}

