package com.example.praktikum_7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout, btnSettings;

    // SharedPreferences untuk status login
    private SharedPreferences loginPrefs;
    private static final String LOGIN_PREF_NAME = "LoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_LOGGED_IN_USERNAME = "loggedInUsername";

    // SharedPreferences untuk tema
    private SharedPreferences themePrefs;
    private static final String THEME_PREF_NAME = "ThemePrefs";
    private static final String KEY_THEME_MODE = "themeMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // --- Terapkan tema sebelum setContentView ---
        applyTheme();
        // ------------------------------------------
        setContentView(R.layout.activity_main);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);
        btnSettings = findViewById(R.id.btnSettings);

        loginPrefs = getSharedPreferences(LOGIN_PREF_NAME, Context.MODE_PRIVATE);

        // Ambil username dari Intent atau SharedPreferences
        String username = getIntent().getStringExtra("username");
        if (username == null || username.isEmpty()) {
            // Jika Intent tidak membawa username (misal, dari status login persisten)
            username = loginPrefs.getString(KEY_LOGGED_IN_USERNAME, "Pengguna");
        }
        tvWelcome.setText("Selamat Datang, " + username + "!");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hapus status login dari SharedPreferences
                SharedPreferences.Editor editor = loginPrefs.edit();
                editor.putBoolean(KEY_IS_LOGGED_IN, false);
                editor.remove(KEY_LOGGED_IN_USERNAME);
                editor.apply();

                Toast.makeText(MainActivity.this, "Anda telah logout.", Toast.LENGTH_SHORT).show();

                // Pindah kembali ke LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void applyTheme() {
        themePrefs = getSharedPreferences(THEME_PREF_NAME, Context.MODE_PRIVATE);
        int themeMode = themePrefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(themeMode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Panggil applyTheme() lagi di onResume untuk memastikan tema diperbarui
        // setelah kembali dari SettingsActivity
        applyTheme();
    }
}