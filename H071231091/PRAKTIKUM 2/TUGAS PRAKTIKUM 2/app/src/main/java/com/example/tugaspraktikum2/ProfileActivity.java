package com.example.tugaspraktikum2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imgProfile;
    private TextView tvNama, tvNIM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgProfile = findViewById(R.id.imgProfile);
        tvNama = findViewById(R.id.tvNama);
        tvNIM = findViewById(R.id.tvNIM);

        Intent intent = getIntent();
        if (intent != null) {

            String nama = intent.getStringExtra("NAMA");
            String nim = intent.getStringExtra("NIM");

            if (nama != null && !nama.isEmpty()) {
                tvNama.setText(nama);
            }

            if (nim != null && !nim.isEmpty()) {
                tvNIM.setText(nim);
            }

            String imageUriString = intent.getStringExtra("IMAGE_URI");
            if (imageUriString != null && !imageUriString.isEmpty()) {
                Uri imageUri = Uri.parse(imageUriString);
                imgProfile.setImageURI(imageUri);
            }
        }
    }
}