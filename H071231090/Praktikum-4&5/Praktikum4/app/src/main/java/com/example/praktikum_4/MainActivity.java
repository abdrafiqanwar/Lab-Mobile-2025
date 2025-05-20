package com.example.praktikum_4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    Button navHome, navAdd, navFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navHome = findViewById(R.id.nav_home);
        navAdd = findViewById(R.id.nav_add);
        navFavorites = findViewById(R.id.nav_favorites);

        loadFragment(new HomeFragment());

        navHome.setOnClickListener(v -> loadFragment(new HomeFragment()));
        navAdd.setOnClickListener(v -> loadFragment(new AddBookFragment()));
        navFavorites.setOnClickListener(v -> loadFragment(new FavoritesFragment()));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
