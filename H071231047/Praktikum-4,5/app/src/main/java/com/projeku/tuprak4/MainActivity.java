package com.projeku.tuprak4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge; // Untuk mendukung desain edge-to-edge
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate; // Untuk mengatur mode malam atau terang
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat; // Untuk menangani padding dan window insets
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.projeku.tuprak4.fragment.AddFragment;
import com.projeku.tuprak4.fragment.FavoritesFragment;
import com.projeku.tuprak4.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    // Method untuk memuat fragment ke dalam kontainer fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit(); // Menyelesaikan transaksi
    }

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Mengaktifkan desain edge-to-edge agar konten bisa memenuhi layar sepenuhnya
        setContentView(R.layout.activity_main); // Menetapkan layout activity_main.xml

        // Menangani Window Insets (padding untuk sistem bar seperti status bar dan navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Mengambil insets untuk sistem bar
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Menetapkan padding untuk elemen
            return insets; // Mengembalikan insets agar sistem bisa memprosesnya
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        bottomNav = findViewById(R.id.bottom_navigation);

        loadFragment(new HomeFragment());

        // Menetapkan listener untuk perubahan item yang dipilih di bottom navigation
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null; // Menyimpan fragment yang dipilih

            int itemId = item.getItemId(); // Mengambil ID item yang dipilih
            // Memeriksa ID item dan memuat fragment sesuai dengan item yang dipilih
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_add) {
                selectedFragment = new AddFragment();
            } else if (itemId == R.id.nav_favorites) {
                selectedFragment = new FavoritesFragment();
            }


            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }

            return false;
        });
    }
}
