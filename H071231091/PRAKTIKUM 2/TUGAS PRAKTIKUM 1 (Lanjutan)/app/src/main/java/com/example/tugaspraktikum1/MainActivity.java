package com.example.tugaspraktikum1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int EDIT_PROFILE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Default values will be taken from XML
        ImageView profileImage = findViewById(R.id.profileImage);
        profileImage.setTag(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.mlluoyii));
    }

    public void onEditProfileClick(View view) {
        TextView nameText = findViewById(R.id.nameText);
        TextView phoneText = findViewById(R.id.phoneText);
        ImageView profileImage = findViewById(R.id.profileImage);

        Intent intent = new Intent(this, EditProfileActivity.class);

        intent.putExtra("name", nameText.getText().toString());
        intent.putExtra("phone", phoneText.getText().toString());
        intent.putExtra("imageUri", profileImage.getTag().toString());
        startActivityForResult(intent, EDIT_PROFILE_REQUEST);
    }

    // Method ini dipanggil saat kembali dari halaman edit
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK && data != null) {
            TextView nameText = findViewById(R.id.nameText);
            TextView phoneText = findViewById(R.id.phoneText);
            ImageView profileImage = findViewById(R.id.profileImage);

            nameText.setText(data.getStringExtra("name"));
            phoneText.setText(data.getStringExtra("phone"));
            String imageUri = data.getStringExtra("imageUri");
            if (imageUri != null) {
                profileImage.setImageURI(Uri.parse(imageUri));
                profileImage.setTag(Uri.parse(imageUri));
            }
        }
    }
}