package com.example.praktikum_7;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegUsername, etRegPassword;
    private Button btnSubmitRegister;

    // SharedPreferences untuk data registrasi pengguna
    private SharedPreferences userRegisterPrefs;
    private static final String USER_REGISTER_PREF_NAME = "UserRegisterPrefs";
    private static final String KEY_REGISTERED_USERNAME = "registeredUsername";
    private static final String KEY_REGISTERED_PASSWORD = "registeredPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnSubmitRegister = findViewById(R.id.btnSubmitRegister);

        // Inisialisasi SharedPreferences
        userRegisterPrefs = getSharedPreferences(USER_REGISTER_PREF_NAME, Context.MODE_PRIVATE);

        btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etRegUsername.getText().toString().trim();
                String password = etRegPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Username dan Password tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                } else {
                    // Simpan data user ke SharedPreferences (akan menimpa yang sebelumnya)
                    SharedPreferences.Editor editor = userRegisterPrefs.edit();
                    editor.putString(KEY_REGISTERED_USERNAME, username);
                    editor.putString(KEY_REGISTERED_PASSWORD, password);
                    editor.apply();

                    Toast.makeText(RegisterActivity.this, "Akun berhasil didaftarkan: " + username, Toast.LENGTH_LONG).show();
                    finish(); // Kembali ke LoginActivity setelah registrasi
                }
            }
        });
    }
}