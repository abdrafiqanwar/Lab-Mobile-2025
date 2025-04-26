package com.example.tp1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private CircleImageView profileImageView;
    private EditText nameEditText, bioEditText, universityEditText, locationEditText, statusEditText;
    private Button saveButton;
    private Uri imageUri;
    private boolean isPhotoChanged = false;

    ActivityResultLauncher<Intent> openGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            imageUri = data.getData();
                            // Persist URI permission
                            int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                            getContentResolver().takePersistableUriPermission(imageUri, takeFlags);
                            profileImageView.setImageURI(imageUri);
                            isPhotoChanged = true;
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Tambahkan tombol back di ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Profil");
        }

        // Inisialisasi views
        profileImageView = findViewById(R.id.profile_image);
        nameEditText = findViewById(R.id.edit_name);
        statusEditText = findViewById(R.id.edit_status);
        bioEditText = findViewById(R.id.edit_bio);
        universityEditText = findViewById(R.id.company_edit);
        locationEditText = findViewById(R.id.location_edit);
        saveButton = findViewById(R.id.btn_save);

        // Ambil data dari intent
        loadDataFromIntent();

        // Set listener untuk tombol ubah foto
//        profileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openFileChooser();
//            }
//        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukagaleri = new Intent(Intent.ACTION_GET_CONTENT);
                bukagaleri.setType("image/*");
                openGallery.launch(Intent.createChooser(bukagaleri, "Choose a picture"));
            }
        });

        // Set listener untuk tombol simpan
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileChanges();
            }
        });
    }
//
//    // Handle tombol back di ActionBar
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    private void loadDataFromIntent() {
        ProfileData profileData = getIntent().getParcelableExtra("profile");
        if (profileData != null) {
            nameEditText.setText(profileData.getName());
            statusEditText.setText(profileData.getStatus());
            bioEditText.setText(profileData.getBio());
            universityEditText.setText(profileData.getUniversity());
            locationEditText.setText(profileData.getLocation());

            imageUri = profileData.getProfileImageUri();
            if (imageUri != null) {
                Glide.with(this).load(imageUri).error(R.drawable.oip).into(profileImageView);
            } else {
                profileImageView.setImageResource(R.drawable.oip);
            }
        } else {
            nameEditText.setText("");
            statusEditText.setText("");
            bioEditText.setText("");
            universityEditText.setText("");
            locationEditText.setText("");
            profileImageView.setImageResource(R.drawable.oip);
        }
    }
    private void saveProfileChanges() {
        // Mengambil data yang diinput
        String name = nameEditText.getText().toString().trim();
        String bio = bioEditText.getText().toString().trim();
        String status = statusEditText.getText().toString().trim();
        String university = universityEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        // Validasi input sederhana
        if (name.isEmpty()) {
            nameEditText.setError("Nama tidak boleh kosong");
            return;
        }

        ProfileData profileData = new ProfileData(name, bio, status, university, location, imageUri);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("profile", profileData);
        setResult(RESULT_OK, resultIntent);
        finish();

        Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
    }
}