package com.example.praktikum3.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.praktikum3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PostDetailActivity extends AppCompatActivity {

    private ImageView ivDetailPost, btnBack;
    private TextView tvDetailCaption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail); // Mengatur layout untuk activity ini


        ivDetailPost = findViewById(R.id.iv_detail_post);
        tvDetailCaption = findViewById(R.id.tv_detail_caption);
        TextView tvDetailUsername = findViewById(R.id.tv_username);
        ImageView ivUserProfile = findViewById(R.id.iv_user_profile);
        btnBack = findViewById(R.id.btn_back);

        // Mengambil data dari Intent
        String caption = getIntent().getStringExtra("feed_caption");
        int imageRes = getIntent().getIntExtra("feed_image", -1); // imageres
        String imageUriString = getIntent().getStringExtra("feed_image_uri"); // URI gambar postingan
        String username = getIntent().getStringExtra("username"); // Username pengguna
        int userProfileImageRes = getIntent().getIntExtra("user_profile_image", -1); // Resource ID gambar profil

        // Menampilkan username ke TextView
        tvDetailUsername.setText(username);

        // Menampilkan gambar profil ke ImageView
        if (userProfileImageRes != -1) {
            Glide.with(this)
                    .load(userProfileImageRes) // Memuat gambar dari resource ID
                    .into(ivUserProfile);
        }

        // Menampilkan gambar postingan
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString); // Mengubah string URI menjadi objek URI
            Glide.with(this)
                    .load(imageUri) // Memuat gambar dari URI
                    .into(ivDetailPost);
        } else if (imageRes != -1) {
            Glide.with(this)
                    .load(imageRes) // Memuat gambar dari resource ID
                    .into(ivDetailPost);
        }

        // Menampilkan caption
        tvDetailCaption.setText(caption);


        btnBack.setOnClickListener(v -> finish());


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_post) {
                startActivity(new Intent(PostDetailActivity.this, PostActivity.class)); // Pindah ke PostActivity
                return true;
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(PostDetailActivity.this, MainActivity.class)); // Pindah ke MainActivity
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(PostDetailActivity.this, ProfileActivity.class)); // Pindah ke ProfileActivity
                return true;
            }
            return false;
        });
    }
}