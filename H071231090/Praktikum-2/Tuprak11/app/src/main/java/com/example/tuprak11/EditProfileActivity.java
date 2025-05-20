package com.example.tuprak11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText editUsername, editName, editBio;
    Button btnSimpan;
    ImageView imgProfilePic;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imgProfilePic.setImageURI(selectedImageUri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editUsername = findViewById(R.id.editUsername);
        editName = findViewById(R.id.editName);
        editBio = findViewById(R.id.editBio);
        btnSimpan = findViewById(R.id.btnSimpan);
        imgProfilePic = findViewById(R.id.imgProfilePic);

        //data user
        String username = "idawahida";
        String name = "Nur Wahida";
        String bio = "SyntaxError";

        // Set data ke EditText
        editUsername.setText(username);
        editName.setText(name);
        editBio.setText(bio);

        imgProfilePic.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), PICK_IMAGE_REQUEST);
        });

        btnSimpan.setOnClickListener(v -> {
            String newUsername = editUsername.getText().toString();
            String newName = editName.getText().toString();
            String newBio = editBio.getText().toString();

            // Kirim data ke MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("username", newUsername);
            resultIntent.putExtra("name", newName);
            resultIntent.putExtra("bio", newBio);

            if (selectedImageUri != null) {
                resultIntent.putExtra("imageUri", selectedImageUri.toString());
            }

            setResult(RESULT_OK, resultIntent);
            finish(); // kembali ke MainActivity
        });
    }
}
