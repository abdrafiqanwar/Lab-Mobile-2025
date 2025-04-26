package com.example.memuemulator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity2 extends AppCompatActivity {
    private EditText editName, editUsername, editEmail;
    private Button saveButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ShapeableImageView avatarImage;
    private String avatarUriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);


        // Form EditText
        editName = findViewById(R.id.name);
        editUsername = findViewById(R.id.username);
        editEmail = findViewById(R.id.email);
        avatarImage = findViewById(R.id.profile_image);
        saveButton = findViewById(R.id.save_button);


        User user = getIntent().getParcelableExtra("user");


        editName.setEnabled(true);
        editUsername.setEnabled(true);
        editEmail.setEnabled(true);
        saveButton.setEnabled(true);


        if (user != null) {
            editName.setText(user.getName());
            editUsername.setText(user.getUsername());
            editEmail.setText(user.getEmail());
            if (user.getAvatarUri() != null) {
                avatarImage.setImageURI(Uri.parse(user.getAvatarUri()));
            }
        }



        saveButton.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String username = editUsername.getText().toString();
            String email = editEmail.getText().toString();

            User updateUser = new User(name, username, email, avatarUriString);

            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            intent.putExtra("user",updateUser);
            startActivity(intent);
            finish();
        });


        // Menambahkan listener untuk menangani klik pada ImageView Back Button
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Manambah listener untuk mengganti avatar
        TextView changeAvatar = findViewById(R.id.change_avatar);
        changeAvatar.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            avatarUriString = imageUri.toString();
            avatarImage.setImageURI(imageUri);
        }
    }

}