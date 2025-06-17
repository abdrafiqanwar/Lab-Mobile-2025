package com.example.praktikum_7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    // SharedPreferences untuk status login
    private SharedPreferences loginPrefs;
    private static final String LOGIN_PREF_NAME = "LoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_LOGGED_IN_USERNAME = "loggedInUsername"; // Untuk menyimpan username yang sedang login

    // SharedPreferences untuk data registrasi pengguna
    private SharedPreferences userRegisterPrefs;
    private static final String USER_REGISTER_PREF_NAME = "UserRegisterPrefs";
    private static final String KEY_REGISTERED_USERNAME = "registeredUsername";
    private static final String KEY_REGISTERED_PASSWORD = "registeredPassword";

    // SharedPreferences untuk tema
    private SharedPreferences themePrefs;
    private static final String THEME_PREF_NAME = "ThemePrefs";
    private static final String KEY_THEME_MODE = "themeMode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- Terapkan tema sebelum setContentView ---
        themePrefs = getSharedPreferences(THEME_PREF_NAME, Context.MODE_PRIVATE);
        int themeMode = themePrefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); // Default: ikuti sistem
        AppCompatDelegate.setDefaultNightMode(themeMode);
        // ------------------------------------------

        // Inisialisasi SharedPreferences
        loginPrefs = getSharedPreferences(LOGIN_PREF_NAME, Context.MODE_PRIVATE);
        userRegisterPrefs = getSharedPreferences(USER_REGISTER_PREF_NAME, Context.MODE_PRIVATE);

        // Cek apakah pengguna sudah login sebelumnya
        if (loginPrefs.getBoolean(KEY_IS_LOGGED_IN, false)) {
            // Jika sudah login, langsung pindah ke MainActivity
            navigateToMainActivity(loginPrefs.getString(KEY_LOGGED_IN_USERNAME, ""));
            return;
        }

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = etUsername.getText().toString().trim();
                String inputPassword = etPassword.getText().toString().trim();

                // Dapatkan data akun yang terdaftar
                String registeredUsername = userRegisterPrefs.getString(KEY_REGISTERED_USERNAME, null);
                String registeredPassword = userRegisterPrefs.getString(KEY_REGISTERED_PASSWORD, null);

                if (registeredUsername == null || registeredPassword == null) {
                    Toast.makeText(LoginActivity.this, "Belum ada akun terdaftar. Silakan Register.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifikasi login
                if (inputUsername.equals(registeredUsername) && inputPassword.equals(registeredPassword)) {
                    // Simpan status login ke SharedPreferences
                    SharedPreferences.Editor editor = loginPrefs.edit();
                    editor.putBoolean(KEY_IS_LOGGED_IN, true);
                    editor.putString(KEY_LOGGED_IN_USERNAME, inputUsername); // Simpan username yang login
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                    navigateToMainActivity(inputUsername);
                } else {
                    Toast.makeText(LoginActivity.this, "Username atau Password salah.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void navigateToMainActivity(String username) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("username", username); // Kirim username ke MainActivity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}