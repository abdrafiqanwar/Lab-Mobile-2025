package com.example.danaui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tvUserName;
    private CircleImageView ivUserProfile;
    private TextView tvUserPhone;
    private LinearLayout profileMenuButton;
    private Uri currentImageUri = null;

    // Gunakan ActivityResultLauncher
    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String newName = data.getStringExtra(EditProfileActivity.EXTRA_NAME);
                    String newPhone = data.getStringExtra(EditProfileActivity.EXTRA_PHONE);
                    String imageUriString = data.getStringExtra(EditProfileActivity.EXTRA_IMAGE_URI);

                    Log.d(TAG, "Received updated data - Name: " + newName + ", Phone: " + newPhone + ", Uri: " + imageUriString);

                    // Update UI langsung dengan data dari Intent
                    tvUserName.setText(newName);
                    tvUserPhone.setText(newPhone);

                    if (imageUriString != null) {
                        try {
                            currentImageUri = Uri.parse(imageUriString);
                            Glide.with(this)
                                    .load(currentImageUri)
                                    .placeholder(R.drawable.ic_default_profile) // Gambar sementara saat loading
                                    .error(R.drawable.ic_default_profile) // Gambar jika gagal load
                                    .into(ivUserProfile);
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing or loading image URI from result: " + imageUriString, e);
                            ivUserProfile.setImageResource(R.drawable.ic_default_profile); // Kembali ke default jika error
                            currentImageUri = null;
                        }
                    } else {
                        ivUserProfile.setImageResource(R.drawable.ic_default_profile); // Set default jika tidak ada gambar
                        currentImageUri = null;
                        Log.d(TAG, "No image URI returned, setting default profile picture.");
                    }

                    Log.d(TAG, "Profile UI updated.");

                } else {
                    Log.d(TAG, "Edit profile cancelled or failed.");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Views
        tvUserName = findViewById(R.id.tvUserName);
        ivUserProfile = findViewById(R.id.ivUserProfile);
        tvUserPhone = findViewById(R.id.tvUserPhone);
        profileMenuButton = findViewById(R.id.llProfileMenu);

        // --- Set Nilai Default Langsung ---
        tvUserName.setText("Muh. Tegar Adyaksa");
        tvUserPhone.setText("081354656590");
        ivUserProfile.setImageResource(R.drawable.ic_default_profile);
        currentImageUri = null; // Pastikan null diawal
        Log.d(TAG, "MainActivity created, UI set to default values.");


        // Setup Listener untuk tombol edit (sesuaikan dengan view Anda)
        profileMenuButton.setOnClickListener(v -> {
            Log.d(TAG, "Edit profile button clicked.");
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);

            // Kirim data *saat ini* yang ada di UI ke EditProfileActivity
            String currentName = tvUserName.getText().toString();
            String currentPhone = tvUserPhone.getText().toString();
            String currentUriString = (currentImageUri != null) ? currentImageUri.toString() : null;

            intent.putExtra(EditProfileActivity.EXTRA_NAME, currentName);
            intent.putExtra(EditProfileActivity.EXTRA_PHONE, currentPhone);
            intent.putExtra(EditProfileActivity.EXTRA_IMAGE_URI, currentUriString);

            Log.d(TAG, "Starting EditProfileActivity with current data - Name: " + currentName + ", Phone: " + currentPhone + ", Uri: " + currentUriString);
            editProfileLauncher.launch(intent);
        });
    }
}