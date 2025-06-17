package com.example.tugaspraktikum1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private TextView profileNameTextView, usernameTextView, bioTextView, descTextView, univTextView, alamatTextView;
    private ShapeableImageView profileImage;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    // Update text fields
                    profileNameTextView.setText(data.getStringExtra("nama"));
                    usernameTextView.setText(data.getStringExtra("userName"));
                    bioTextView.setText(data.getStringExtra("bio"));
                    descTextView.setText(data.getStringExtra("deskripsi"));
                    univTextView.setText(data.getStringExtra("univ"));
                    alamatTextView.setText(data.getStringExtra("alamat"));

                    // Update profile image
                    String imageUri = data.getStringExtra("profile_image");
                    if (imageUri != null && !imageUri.isEmpty()) {
                        try {
                            Uri uri = Uri.parse(imageUri);
                            profileImage.setImageURI(null); // Clear existing image
                            profileImage.setImageURI(uri); // Set new image
                            profileImage.setTag(imageUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        Button editProfileButton = findViewById(R.id.EditProfileButton);
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("nama", profileNameTextView.getText().toString());
            intent.putExtra("userName", usernameTextView.getText().toString());
            intent.putExtra("bio", bioTextView.getText().toString());
            intent.putExtra("deskripsi", descTextView.getText().toString());
            intent.putExtra("univ", univTextView.getText().toString());
            intent.putExtra("alamat", alamatTextView.getText().toString());

            if (profileImage.getDrawable() != null) {
                Object tag = profileImage.getTag();
                if (tag != null && tag instanceof String) {
                    intent.putExtra("profile_image", (String) tag);
                }
            }

            editProfileLauncher.launch(intent);
        });
    }

    private void initializeViews() {
        profileNameTextView = findViewById(R.id.profileName);
        usernameTextView = findViewById(R.id.username);
        bioTextView = findViewById(R.id.bio);
        descTextView = findViewById(R.id.desc);
        univTextView = findViewById(R.id.univ);
        alamatTextView = findViewById(R.id.alamat);
        profileImage = findViewById(R.id.profileImage);
    }
}