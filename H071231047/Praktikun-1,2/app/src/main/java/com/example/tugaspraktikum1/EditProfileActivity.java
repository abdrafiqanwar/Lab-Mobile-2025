package com.example.tugaspraktikum1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private TextInputEditText editNama, editUserName, editBio, editDeskripsi, editUniv, editAlamat;
    private MaterialButton saveButton;
    private ShapeableImageView profileImageView;
    private TextView changePhotoText;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        initializeViews();
        loadExistingData();

        profileImageView.setOnClickListener(v -> openImagePicker());
        changePhotoText.setOnClickListener(v -> openImagePicker());
        saveButton.setOnClickListener(v -> saveProfileChanges());

        ImageView backIcon = findViewById(R.id.ikonBack);
        backIcon.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void initializeViews() {
        editNama = findViewById(R.id.editName);
        editUserName = findViewById(R.id.editUsername);
        editBio = findViewById(R.id.editBio);
        editDeskripsi = findViewById(R.id.editDesc);
        editUniv = findViewById(R.id.editUniv);
        editAlamat = findViewById(R.id.editLoc);
        saveButton = findViewById(R.id.saveButton);
        profileImageView = findViewById(R.id.profileImage);
        changePhotoText = findViewById(R.id.textView);
    }

    private void loadExistingData() {
        Intent intent = getIntent();
        editNama.setText(intent.getStringExtra("nama"));
        editUserName.setText(intent.getStringExtra("userName"));
        editBio.setText(intent.getStringExtra("bio"));
        editDeskripsi.setText(intent.getStringExtra("deskripsi"));
        editUniv.setText(intent.getStringExtra("univ"));
        editAlamat.setText(intent.getStringExtra("alamat"));

        String imageUriString = intent.getStringExtra("profile_image");
        Log.d("ProfileDebug", "Loading existing image: " + imageUriString);

        if (imageUriString != null && !imageUriString.isEmpty()) {
            selectedImageUri = Uri.parse(imageUriString);
            Glide.with(this)
                    .load(selectedImageUri)
                    .error(R.drawable.fotoprofilkucing)
                    .into(profileImageView);
            profileImageView.setTag(imageUriString);
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Log.d("ProfileDebug", "New image selected: " + selectedImageUri);

            if (selectedImageUri != null) {
                try {
                    getContentResolver().takePersistableUriPermission(
                            selectedImageUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );

                    Glide.with(this)
                            .load(selectedImageUri)
                            .error(R.drawable.fotoprofilkucing)
                            .into(profileImageView);
                    profileImageView.setTag(selectedImageUri.toString());

                } catch (Exception e) {
                    Log.e("ProfileDebug", "Error loading image", e);
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveProfileChanges() {
        String newNama = editNama.getText().toString().trim();
        String newUserName = editUserName.getText().toString().trim();

        if (newNama.isEmpty() || newUserName.isEmpty()) {
            Toast.makeText(this, "Nama dan username tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("nama", newNama);
        resultIntent.putExtra("userName", newUserName);
        resultIntent.putExtra("bio", editBio.getText().toString().trim());
        resultIntent.putExtra("deskripsi", editDeskripsi.getText().toString().trim());
        resultIntent.putExtra("univ", editUniv.getText().toString().trim());
        resultIntent.putExtra("alamat", editAlamat.getText().toString().trim());

        // Handle image URI
        if (selectedImageUri != null) {
            String imageUriString = selectedImageUri.toString();
            Log.d("ProfileDebug", "Saving image URI: " + imageUriString);
            resultIntent.putExtra("profile_image", imageUriString);
        } else {
            Object imageTag = profileImageView.getTag();
            if (imageTag != null && imageTag instanceof String) {
                Log.d("ProfileDebug", "Saving image from tag: " + imageTag);
                resultIntent.putExtra("profile_image", (String) imageTag);
            }
        }

        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}