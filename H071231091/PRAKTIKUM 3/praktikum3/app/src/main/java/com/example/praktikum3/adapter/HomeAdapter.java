package com.example.praktikum3.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum3.R;
import com.example.praktikum3.activity.OtherProfileActivity;
import com.example.praktikum3.jclass.Home;

import java.util.ArrayList;

// menampilkan daftar postingan di halaman utama (Home)
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private ArrayList<Home> homeList;
    private Context context;

    // Konstruktor adapter: menerima context dan list data
    public HomeAdapter(Context context, ArrayList<Home> homeList) {
        this.context = context;
        this.homeList = homeList;
    }

    //menyiapkan tampilan kosong untuk diisi di onbindviewholder
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_feed.xml ke dalam objek View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Home home = homeList.get(position); // Ambil data berdasarkan posisi

        // Load gambar profil dengan Glide (library untuk load gambar dari URL atau resource)
        Glide.with(context)
                .load(home.getProfileImage())
                .into(holder.profileImage);

        // Load gambar postingan
        Glide.with(context)
                .load(home.getPostImage())
                .into(holder.postImage);

        // Gabungkan username (huruf tebal) dan caption (biasa)
        String fullText = home.getUsername() + " " + home.getCaption();
        SpannableString spannableString = new SpannableString(fullText);

        // Buat bagian username menjadi bold
        spannableString.setSpan(
                new StyleSpan(Typeface.BOLD), // Gaya huruf tebal
                0, // Mulai dari index 0
                home.getUsername().length(), // Sampai akhir username
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        //menggabungkan username dan caption
        holder.caption.setText(spannableString);
        holder.username.setText(home.getUsername());

        // Saat foto profil atau username diklik, buka profil orang lain
        holder.profileImage.setOnClickListener(v -> openOtherProfile(home));
        holder.username.setOnClickListener(v -> openOtherProfile(home));
    }

    // Fungsi untuk membuka OtherProfileActivity
    private void openOtherProfile(Home home) {
        Intent intent = new Intent(context, OtherProfileActivity.class);
        intent.putExtra("username", home.getUsername());
        intent.putExtra("profileImage", home.getProfileImage());
        intent.putIntegerArrayListExtra("postList", home.getPostList());
        context.startActivity(intent); // Mulai aktivitas
    }

    // Mengembalikan jumlah item dalam list (untuk RecyclerView)
    @Override
    public int getItemCount() {
        return homeList.size();
    }

    // ViewHolder: menyimpan referensi ke komponen UI dalam item layout
    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage, postImage;
        TextView caption, username;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            // Dibuat setiap kali RecyclerView butuh tampilan
            profileImage = itemView.findViewById(R.id.iv_profile);
            postImage = itemView.findViewById(R.id.iv_post);
            caption = itemView.findViewById(R.id.tv_caption);
            username = itemView.findViewById(R.id.tv_username);
        }
    }
}
