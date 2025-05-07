package com.example.praktikum3.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum3.jclass.Home;
import com.example.praktikum3.adapter.HomeAdapter;
import com.example.praktikum3.data.HomeData;
import com.example.praktikum3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvHome;
    private HomeAdapter homeAdapter;
    private ArrayList<Home> homeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup RecyclerView untuk menampilkan feed
        rvHome = findViewById(R.id.rv_feed);
        rvHome.setLayoutManager(new LinearLayoutManager(this));

        // Ambil data dan set adapter
        homeList = HomeData.getHomeList();
        homeAdapter = new HomeAdapter(this, homeList);
        rvHome.setAdapter(homeAdapter);

        // Setup bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home); // Set home sebagai aktif default

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_post) {
                startActivity(new Intent(this, PostActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data saat kembali ke activity
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
    }
}