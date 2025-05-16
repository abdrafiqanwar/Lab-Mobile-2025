package com.example.praktikum4.adapter;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum4.R;
import com.example.praktikum4.jclass.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> implements Filterable {
    private final Context context;
    private final List<Book> originalBooks; // Data asli
    private List<Book> filteredBooks;       // Data buku yang sudah difilter
    private final OnBookClickListener listener;
    private FilterListener filterListener;


    // click book
    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    // Digunakan untuk mengirim jumlah hasil filter ke fragment
    public interface FilterListener {
        void onFilterResult(int resultCount);
    }

    // Constructor: menyimpan context, data, dan listener klik
    public BookAdapter(Context context, List<Book> books, OnBookClickListener listener) {
        this.context = context;
        this.originalBooks = new ArrayList<>(books); // Simpan data asli
        this.filteredBooks = books; // Data yang akan ditampilkan
        this.listener = listener;
    }

    // Untuk mengatur hasil filter
    public void setFilterListener(FilterListener listener) {
        this.filterListener = listener;
    }

    // Membuat tampilan item buku dari XML (item_book.xml)
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    // Mengisi tampilan item dengan data dari buku
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = filteredBooks.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());

        // Menampilkan gambar cover menggunakan Glide
        Glide.with(context)
                .load(book.getCoverImageUri())
                .into(holder.coverImageView);

        // Saat gambar cover diklik, jalankan listener
        holder.coverImageView.setOnClickListener(v -> listener.onBookClick(book));
    }

    // Mengembalikan jumlah item yang sedang ditampilkan
    @Override
    public int getItemCount() {
        return filteredBooks.size();
    }

    // Mengembalikan filter yang akan digunakan saat mencari
    @Override
    public Filter getFilter() {
        return bookFilter;
    }

    // Filter kustom untuk pencarian buku berdasarkan judul
    private final Filter bookFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = (constraint == null) ? "" : constraint.toString().toLowerCase().trim();
            List<Book> tempList = new ArrayList<>();

            // Cek apakah judul buku mengandung kata kunci pencarian
            for (Book book : originalBooks) {
                boolean cocok = query.isEmpty() || book.getTitle().toLowerCase().contains(query);
                if (cocok) {
                    tempList.add(book);
                }
            }

            FilterResults results = new FilterResults();
            results.values = tempList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredBooks = (List<Book>) results.values;
            notifyDataSetChanged(); // Update tampilan setelah difilter

            // Kirim jumlah hasil filter ke listener jika ada
            if (filterListener != null) {
                filterListener.onFilterResult(filteredBooks.size());
            }
        }
    };

    // Menyimpan tampilan yang ada di item_book.xml
    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImageView;
        TextView titleTextView, authorTextView;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.image_view_cover);
            titleTextView  = itemView.findViewById(R.id.text_view_title);
            authorTextView = itemView.findViewById(R.id.text_view_author);
        }
    }
}
