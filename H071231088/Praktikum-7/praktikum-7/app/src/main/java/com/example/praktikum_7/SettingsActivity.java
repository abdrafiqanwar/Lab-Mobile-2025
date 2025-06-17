package com.example.praktikum_7;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup rgThemeMode;
    private Button btnSaveTheme;
    private SharedPreferences themePrefs;

    private static final String THEME_PREF_NAME = "ThemePrefs";
    private static final String KEY_THEME_MODE = "themeMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        rgThemeMode = findViewById(R.id.rgThemeMode);
        btnSaveTheme = findViewById(R.id.btnSaveTheme);

        themePrefs = getSharedPreferences(THEME_PREF_NAME, Context.MODE_PRIVATE);

        // Atur pilihan RadioGroup berdasarkan tema yang tersimpan
        int currentThemeMode = themePrefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        if (currentThemeMode == AppCompatDelegate.MODE_NIGHT_NO) {
            rgThemeMode.check(R.id.rbLightMode);
        } else if (currentThemeMode == AppCompatDelegate.MODE_NIGHT_YES) {
            rgThemeMode.check(R.id.rbDarkMode);
        } else {
            rgThemeMode.check(R.id.rbSystemMode);
        }

        btnSaveTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = rgThemeMode.getCheckedRadioButtonId();
                int newThemeMode;

                if (checkedId == R.id.rbLightMode) {
                    newThemeMode = AppCompatDelegate.MODE_NIGHT_NO;
                } else if (checkedId == R.id.rbDarkMode) {
                    newThemeMode = AppCompatDelegate.MODE_NIGHT_YES;
                } else {
                    newThemeMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                }

                // Simpan pilihan tema ke SharedPreferences
                SharedPreferences.Editor editor = themePrefs.edit();
                editor.putInt(KEY_THEME_MODE, newThemeMode);
                editor.apply();

                // Terapkan tema secara langsung
                AppCompatDelegate.setDefaultNightMode(newThemeMode);

                Toast.makeText(SettingsActivity.this, "Tema berhasil diubah!", Toast.LENGTH_SHORT).show();
                finish(); // Tutup SettingsActivity dan kembali ke MainActivity
            }
        });
    }
}