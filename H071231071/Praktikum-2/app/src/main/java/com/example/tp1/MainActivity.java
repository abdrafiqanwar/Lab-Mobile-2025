package com.example.tp1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvUsername, name, tvBio, tvWebsite;
    private Button btnEdit;
    private ImageView ivProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = findViewById(R.id.tvUsername);
        name = findViewById(R.id.user);
        tvBio = findViewById(R.id.tvBio);
        tvWebsite = findViewById(R.id.tvWebsite);
        btnEdit = findViewById(R.id.btnEdit);
        ivProfile = findViewById(R.id.profileImage);
        UserData updatedUserData = getIntent().getParcelableExtra("extra_userdata");

        String username, nama, bio, website;
        Uri imageUri;

        if (updatedUserData != null) {
            username = updatedUserData.getUsername();
            nama = updatedUserData.getName();
            bio = updatedUserData.getBio();
            website = updatedUserData.getWebsite();
            imageUri = updatedUserData.getImageUri();
        } else {
            username = tvUsername.getText().toString();
            nama = name.getText().toString();
            bio = tvBio.getText().toString();
            website = tvWebsite.getText().toString();
            imageUri = (Uri) ivProfile.getTag(); // ambil dari tag jika tidak ada parcelable
        }


        btnEdit.setOnClickListener(v -> {
            UserData userData = new UserData(username, nama, bio, website, imageUri);

            Intent moveToEdit = new Intent(MainActivity.this, EditProfileActivity.class);
            moveToEdit.putExtra("extra_userdata", userData);
            startActivity(moveToEdit);
        });


        if (updatedUserData != null) {
            tvUsername.setText(updatedUserData.getUsername());
            name.setText(updatedUserData.getName());
            tvBio.setText(updatedUserData.getBio());
            tvWebsite.setText(updatedUserData.getWebsite());

            Uri newImageUri = updatedUserData.getImageUri();
            if (newImageUri != null) {
                ivProfile.setImageURI(newImageUri);
                ivProfile.setTag(newImageUri); // penting untuk dikirim lagi ke EditProfileActivity
            } else {
                ivProfile.setImageResource(R.drawable.pp);
            }
        }
    }



}
