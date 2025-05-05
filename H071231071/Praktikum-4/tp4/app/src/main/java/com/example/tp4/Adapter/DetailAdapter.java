
package com.example.tp4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4.Model.Book;
import com.example.tp4.Model.FavoriteStore;
import com.example.tp4.R;

import java.util.List;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    private Context context;
    private List<Book> books;

    public interface OnBackClickListener {
        void onBackClick();
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Book book, boolean isFavorite);
    }

    private OnBackClickListener backClickListener;
    private OnFavoriteClickListener favoriteClickListener;

    public void setOnBackClickListener(OnBackClickListener listener) {
        this.backClickListener = listener;
    }

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.favoriteClickListener = listener;
    }

    public DetailAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_book_item, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        Book book = books.get(position);

        if (book.getCoverUp() != null) {
            holder.image_cover.setImageURI(book.getCoverUp());
        } else {
            holder.image_cover.setImageResource(book.getCover());
        }

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvGenre.setText(book.getGenre());
        holder.tvBlurb.setText(book.getBlurb());
        holder.tvRelease.setText(String.valueOf(book.getRelease_year()));
        holder.rbRating.setRating(book.getRating());


        // Update tombol favorite sesuai status
        updateFavoriteButton(holder.btnFavorite, FavoriteStore.isFavorite(book));

        holder.btnFavorite.setOnClickListener(v -> {
            // Toggle status favorit buku
            FavoriteStore.toggleFavorite(book);

            // Update status tombol favorit
            updateFavoriteButton(holder.btnFavorite, FavoriteStore.isFavorite(book));

            // Menampilkan Toast jika buku berhasil ditambahkan ke favorit
            if (FavoriteStore.isFavorite(book)) {
                Toast.makeText(context, "Buku berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Buku berhasil dihapus dari favorit", Toast.LENGTH_SHORT).show();
            }

            // Memanggil listener jika ada
            if (favoriteClickListener != null) {
                favoriteClickListener.onFavoriteClick(book, FavoriteStore.isFavorite(book));
            }


        });


        holder.icBack.setOnClickListener(v -> {
            if (backClickListener != null) {
                backClickListener.onBackClick();
            }
        });
    }

    private void updateFavoriteButton(Button btnFavorite, boolean isFavorite) {
        if (isFavorite) {
            btnFavorite.setText("Remove from Favorite");
        } else {
            btnFavorite.setText("Add to Favorite");
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        ImageView image_cover;
        ImageButton icBack;
        TextView tvTitle, tvAuthor, tvGenre, tvRelease, tvBlurb;
        RatingBar rbRating;
        Button btnFavorite;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            image_cover = itemView.findViewById(R.id.img_book_cover);
            icBack = itemView.findViewById(R.id.btn_back);
            tvTitle = itemView.findViewById(R.id.tv_book_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            tvRelease = itemView.findViewById(R.id.tv_release_year);
            tvBlurb = itemView.findViewById(R.id.tv_description);
            rbRating = itemView.findViewById(R.id.rating_bar);
            btnFavorite = itemView.findViewById(R.id.btn_borrow);
        }
    }
}