package com.example.tuprak11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageButton btnEdit = findViewById(R.id.btnEditProfile);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivityForResult(intent, EDIT_PROFILE_REQUEST); // <- agar bisa dapat data kembali
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK) {
            String updatedUsername = data.getStringExtra("username");
            String updatedName = data.getStringExtra("name");
            String updatedBio = data.getStringExtra("bio");
            String imageUriString = data.getStringExtra("imageUri");

            // Update tampilan
            TextView txtUsername = findViewById(R.id.txtUsername);
            TextView txtName = findViewById(R.id.txtName);
            TextView txtBio = findViewById(R.id.txtBio);
            ImageView imgProfilePic = findViewById(R.id.imgProfilePic);

            txtUsername.setText(updatedUsername);
            txtName.setText(updatedName);
            txtBio.setText(updatedBio);
            if (imageUriString != null) {
                Uri imageUri = Uri.parse(imageUriString);
                imgProfilePic.setImageURI(imageUri);
            }
        }
    }
}