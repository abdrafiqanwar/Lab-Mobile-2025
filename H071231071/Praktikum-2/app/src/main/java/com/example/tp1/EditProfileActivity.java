package com.example.tp1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etUsername, etName, etBio, etWebsite;
    private TextView tvChangePhoto;
    private ImageView profileImage;
    private ImageButton btnSave, btnBack;
    private Uri selectedImageUri = null;

    private final ActivityResultLauncher<Intent> openGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        try {
                            // Ambil izin persistensi
                            final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                            getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);

                            profileImage.setImageURI(selectedImageUri);
                            profileImage.setTag(selectedImageUri);
                        } catch (Exception e) {
                            Toast.makeText(this, "Gagal memuat gambar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            selectedImageUri = null;
                            profileImage.setImageResource(R.drawable.pp);
                        }
                    } else {
                        Toast.makeText(this, "Gagal memilih gambar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        etUsername = findViewById(R.id.etUsername);
        etName = findViewById(R.id.etName);
        etBio = findViewById(R.id.etBio);
        etWebsite = findViewById(R.id.etWebsite);
        tvChangePhoto = findViewById(R.id.tvChangePhoto);
        profileImage = findViewById(R.id.profileImage);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        UserData userData = getIntent().getParcelableExtra("extra_userdata");

        if (userData != null) {
            etUsername.setText(userData.getUsername());
            etName.setText(userData.getName());
            etBio.setText(userData.getBio());
            etWebsite.setText(userData.getWebsite());

            selectedImageUri = userData.getImageUri();
            if (selectedImageUri != null) {
                try {
                    // Gunakan try-catch untuk menangani URI yang mungkin tidak valid
                    profileImage.setImageURI(selectedImageUri);
                    profileImage.setTag(selectedImageUri);
                } catch (Exception e) {
                    // Jika gagal load gambar, gunakan gambar default
                    profileImage.setImageResource(R.drawable.pp);
                    selectedImageUri = null; 
                }
            } else {
                profileImage.setImageResource(R.drawable.pp);
            }
        }

        tvChangePhoto.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);  // Gunakan ACTION_OPEN_DOCUMENT
            pickIntent.setType("image/*");
            pickIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);  // Tambahkan flag ini
            pickIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);  // Tambahkan flag ini
            openGallery.launch(Intent.createChooser(pickIntent, "Pilih gambar"));
        });

        btnSave.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String bio = etBio.getText().toString().trim();
            String website = etWebsite.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Name tidak boleh kosong");
                etName.requestFocus();
                return;
            }

            if (username.isEmpty()) {
                etUsername.setError("Username tidak boleh kosong");
                etUsername.requestFocus();
                return;
            }

            if (!website.isEmpty() && !Patterns.WEB_URL.matcher(website).matches()) {
                etWebsite.setError("Masukkan URL yang valid (misal: https://example.com)");
                etWebsite.requestFocus();
                return;
            }

            UserData updatedData = new UserData(username, name, bio, website, selectedImageUri);
            Intent resultIntent = new Intent(EditProfileActivity.this, MainActivity.class);
            resultIntent.putExtra("extra_userdata", updatedData);
            startActivity(resultIntent);
//  setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });


        btnBack.setOnClickListener(v -> finish());
    }
}
