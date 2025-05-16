package com.example.instagram;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.instagram.R;

public class StoryDetailActivity extends AppCompatActivity {

    private ImageView storyImage;
    private String imageUri;

    // Launcher for requesting permissions
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    loadImage();
                } else {
                    Toast.makeText(this, "Permission denied. Cannot display image.", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        storyImage = findViewById(R.id.detail_story_image);
        imageUri = getIntent().getStringExtra("imageUri");

        // Check and request permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+: Request READ_MEDIA_IMAGES
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            } else {
                loadImage();
            }
        } else {
            // Android 12 and below: Request READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
                loadImage();
            }
        }
    }

    private void loadImage() {
        if (imageUri != null) {
            Glide.with(this)
                    .load(Uri.parse(imageUri))
                    .error(R.drawable.ic_arrow_back) // Add a placeholder error image in res/drawable
                    .into(storyImage);
        } else {
            Toast.makeText(this, "No image found.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
