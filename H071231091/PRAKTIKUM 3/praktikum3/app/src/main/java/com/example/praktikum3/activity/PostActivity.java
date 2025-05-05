package com.example.praktikum3.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;

import androidx.annotation.Nullable;

import com.example.praktikum3.jclass.Feed;
import com.example.praktikum3.data.FeedData;
import com.example.praktikum3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PostActivity extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1; // Kode permintaan untuk memilih gambar
    private ImageView imagePreview;
    private EditText captionInput;
    private Button btnChooseImage, btnUpload;
    private Uri selectedImageUri = null; // URI gambar yang dipilih

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post); // Mengatur layout activity

        // Inisialisasi
        imagePreview = findViewById(R.id.image_preview);
        captionInput = findViewById(R.id.et_caption);
        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnUpload = findViewById(R.id.btn_upload);

        btnChooseImage.setOnClickListener(v -> openGallery());

        btnUpload.setOnClickListener(v -> {
            String caption = captionInput.getText().toString();
            if (selectedImageUri != null && !caption.isEmpty()) { // Validasi input
                Feed newFeed = new Feed(selectedImageUri, caption);
                FeedData.addFeed(newFeed); // Tambahkan feed ke data
                Toast.makeText(PostActivity.this, "Feed berhasil di-upload!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(PostActivity.this, "Lengkapi gambar atau caption!", Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_post);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(PostActivity.this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(PostActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Menangani hasil dari aktivitas memilih gambar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData(); // Simpan URI gambar yang dipilih
            imagePreview.setImageURI(selectedImageUri); // Tampilkan gambar di pratinjau
        }
    }
}