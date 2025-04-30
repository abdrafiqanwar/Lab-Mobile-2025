package com.example.tp3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp3.Model.Feed;
import com.example.tp3.Model.User;

public class AddPostActivity extends AppCompatActivity {

    private ImageView imagePreview;
    private EditText captionInput;
    private ImageView btnChooseImage;
    private TextView btnPost;

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

                            imagePreview.setImageURI(selectedImageUri);
                            imagePreview.setTag(selectedImageUri);
                        } catch (Exception e) {
                            Toast.makeText(this, "Gagal memuat gambar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            selectedImageUri = null;
                            imagePreview.setImageResource(R.drawable.jennie);
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
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_add_post);

        // Inisialisasi views
        imagePreview = findViewById(R.id.image_preview);
        captionInput = findViewById(R.id.caption_input);
        btnChooseImage = findViewById(R.id.image_preview);
        btnPost = findViewById(R.id.btn_post);

        // Set listener untuk tombol pilih gambar
        btnChooseImage.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);  // Gunakan ACTION_OPEN_DOCUMENT
            pickIntent.setType("image/*");
            pickIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);  // Tambahkan flag ini
            pickIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);  // Tambahkan flag ini
            openGallery.launch(Intent.createChooser(pickIntent, "Pilih gambar"));
        });



        // Set listener untuk tombol post
        btnPost.setOnClickListener(v -> {
            if (selectedImageUri == null) {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
                return;
            }

            // Caption bisa kosong (opsional)
            String caption = captionInput.getText().toString().trim();

            // Ambil user 0
            User user = DataFeedSource.getUsers().get(0);

            // Buat feed baru dengan imageUri yang dipilih
            Feed newFeed = new Feed(user, selectedImageUri, caption);

            // Tambahkan ke user
            user.addFeed(newFeed);

            Toast.makeText(this, "Post added successfully!", Toast.LENGTH_SHORT).show();

            // KIRIM RESULT
            setResult(RESULT_OK);

            // Kembali ke MainActivity
            finish();
        });

        findViewById(R.id.close_button).setOnClickListener(v-> {
            finish();
        });
    }
}
