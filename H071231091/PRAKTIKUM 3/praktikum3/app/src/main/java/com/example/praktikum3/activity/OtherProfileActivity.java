package com.example.praktikum3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum3.R;
import com.example.praktikum3.adapter.OtherUserFeedAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfileActivity extends AppCompatActivity {

    // Deklarasi view untuk komponen UI
    private CircleImageView ivOtherProfile;
    private TextView tvOtherUsername, tvOtherBio;
    private RecyclerView rvOtherProfileFeed; // Untuk menampilkan grid post
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        // Inisialisasi view dari layout
        ivOtherProfile = findViewById(R.id.iv_other_profile);
        tvOtherUsername = findViewById(R.id.tv_other_username);
        tvOtherBio = findViewById(R.id.tv_other_bio);
        rvOtherProfileFeed = findViewById(R.id.rv_other_profile_feed);
        btnBack = findViewById(R.id.btn_back);

        // Ambil data
        String username = getIntent().getStringExtra("username");
        int profileImage = getIntent().getIntExtra("profileImage", -1);
        ArrayList<Integer> postList = getIntent().getIntegerArrayListExtra("postList");

        // Set data
        ivOtherProfile.setImageResource(profileImage); // Set gambar profil
        tvOtherUsername.setText(username); // Set username
        tvOtherBio.setText("Ini bio dari " + username);

        // Setup RecyclerView dengan grid 3 kolom
        rvOtherProfileFeed.setLayoutManager(new GridLayoutManager(this, 3));
        // Buat adapter untuk menampilkan postingan user lain
        OtherUserFeedAdapter adapter = new OtherUserFeedAdapter(this, postList, username, profileImage);
        rvOtherProfileFeed.setAdapter(adapter);

        // Tombol kembali untuk menutup activity
        btnBack.setOnClickListener(v -> finish());

        // Setup bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_profile); // Set menu profile aktif
        bottomNav.setOnItemSelectedListener(item -> {
            // Navigasi antar activity
            int id = item.getItemId();
            if (id == R.id.nav_post) {
                startActivity(new Intent(OtherProfileActivity.this, PostActivity.class));
                return true;
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(OtherProfileActivity.this, MainActivity.class));
                return true;
            }
            else if (id == R.id.nav_profile) {
                startActivity(new Intent(OtherProfileActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }
}