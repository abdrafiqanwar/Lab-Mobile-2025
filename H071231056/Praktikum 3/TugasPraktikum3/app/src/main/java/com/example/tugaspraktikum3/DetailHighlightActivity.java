package com.example.tugaspraktikum3;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class DetailHighlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_highlight);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.highlightDetail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // INIALISASI VARIABEL
        ImageView imageHighlight = findViewById(R.id.imageHighlight);

        // MENGAMBIL DATA DARI INTENT
        int imageResId = getIntent().getIntExtra("IMAGE_RES_ID", -1);



        // TAMPILIN

        if (imageResId != -1) {
            imageHighlight.setImageResource(imageResId);
        }



    }
}