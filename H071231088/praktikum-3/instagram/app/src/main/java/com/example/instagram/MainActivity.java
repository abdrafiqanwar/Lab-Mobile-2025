package com.example.instagram;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.instagram.fragment.FeedUpFragment;
import com.example.instagram.fragment.HomeFragment;
import com.example.instagram.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Memastikan fragment pertama kali muncul saat activity dimulai
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            // Set efek skala untuk ikon default
            scaleIcon(bottomNavigationView.getMenu().findItem(R.id.nav_home), true);
        }

        // Listener saat navigasi ditekan
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Reset semua ikon ke ukuran normal
            for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                scaleIcon(bottomNavigationView.getMenu().getItem(i), false);
            }

            // Perbesar ikon yang dipilih
            scaleIcon(item, true);

            // Logika navigasi
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                Log.d("MainActivity", "HomeFragment selected");
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
                Log.d("MainActivity", "ProfileFragment selected");
            } else if (id == R.id.nav_feedUp) {
                selectedFragment = new FeedUpFragment();
                Log.d("MainActivity", "FeedUpFragment selected");
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });

        // Set item default
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    // Fungsi untuk mengganti fragment di fragment_container
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    // Fungsi untuk mengatur skala ikon
    private void scaleIcon(android.view.MenuItem item, boolean isSelected) {
        if (item.getIcon() != null) {
            float scale = isSelected ? 1.2f : 1.0f; // 20% lebih besar jika terpilih
            item.getIcon().mutate().setBounds(
                    0, 0,
                    (int) (item.getIcon().getIntrinsicWidth() * scale),
                    (int) (item.getIcon().getIntrinsicHeight() * scale)
            );
        }
    }
}