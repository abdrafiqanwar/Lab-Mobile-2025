package com.example.library.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.library.R;
import com.example.library.home.fragment.AddBookFragment;
import com.example.library.home.fragment.FavoriteFragment;
import com.example.library.home.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;

    private int[] prevSelectedId = {-1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ProgressBar progressBar = findViewById(R.id.progressBar);


        loadFragment(new HomeFragment());
        prevSelectedId[0] = R.id.nav_home;

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int selectedItemId = item.getItemId();

            if (selectedItemId == R.id.nav_home) {
                if (selectedItemId != prevSelectedId[0]) {
                    com.example.library.button.SoundPlayer.playClickSound(MainActivity.this);
                }
                loadFragment(new HomeFragment());
            } else if (selectedItemId == R.id.nav_addBook) {
                loadFragment(new AddBookFragment());
            } else if (selectedItemId == R.id.nav_favorite) {
                loadFragment(new FavoriteFragment());
            }
            prevSelectedId[0] = selectedItemId;

            return true;
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
