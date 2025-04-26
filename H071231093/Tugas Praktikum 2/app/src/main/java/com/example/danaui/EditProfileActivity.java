package com.example.danaui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton; // Import
import android.widget.ImageView;
import android.widget.TextView; // Import
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity"; // Untuk logging

    // Kunci untuk Intent Extras (buat public static agar MainActivity bisa akses)
    public static final String EXTRA_NAME = "com.example.danaui.EXTRA_NAME";
    public static final String EXTRA_PHONE = "com.example.danaui.EXTRA_PHONE";
    public static final String EXTRA_IMAGE_URI = "com.example.danaui.EXTRA_IMAGE_URI";

    private CircleImageView ivEditProfilePic;
    private EditText etEditName;
    private EditText etEditPhoneNumber;
    private Button btnSaveChanges;
    private ImageButton btnBack;
    private TextView tvChangePhotoHint; // Opsional

    private Uri selectedImageUri = null; // Menyimpan URI gambar yang dipilih

    // Launcher untuk memilih gambar dari galeri
    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        selectedImageUri = uri;
                        // Ambil persistable permission agar URI bisa diakses nanti oleh MainActivity
                        try {
                            final int takeFlags = result.getData().getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);
                            Log.d(TAG, "Persistable URI permission taken for: " + selectedImageUri.toString());
                        } catch (SecurityException e) {
                            Log.e(TAG, "Failed to take persistable URI permission", e);
                            Toast.makeText(this, "Gagal mendapatkan izin akses gambar.", Toast.LENGTH_SHORT).show();
                            selectedImageUri = null; // Reset jika gagal
                        }

                        // Tampilkan gambar yang dipilih menggunakan Glide (lebih efisien)
                        Glide.with(this)
                                .load(selectedImageUri)
                                .placeholder(R.drawable.ic_default_profile) // Gambar sementara
                                .error(R.drawable.ic_default_profile) // Gambar jika error
                                .into(ivEditProfilePic);

                    } else {
                        Log.w(TAG, "Image selection returned null URI.");
                        Toast.makeText(this, "Gagal memilih gambar.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "Image selection cancelled or failed. Result code: " + result.getResultCode());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivEditProfilePic = findViewById(R.id.ivEditProfilePic);
        etEditName = findViewById(R.id.etEditName);
        etEditPhoneNumber = findViewById(R.id.etEditPhoneNumber);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnBack = findViewById(R.id.btnBack); // Ambil referensi tombol kembali
        tvChangePhotoHint = findViewById(R.id.tvChangePhotoHint); // Opsional

        // Ambil data yang dikirim dari MainActivity
        Intent intent = getIntent();
        String currentName = intent.getStringExtra(EXTRA_NAME);
        String currentPhone = intent.getStringExtra(EXTRA_PHONE);
        String currentImageUriString = intent.getStringExtra(EXTRA_IMAGE_URI);

        Log.d(TAG, "Received initial data - Name: " + currentName + ", Phone: " + currentPhone + ", Uri: " + currentImageUriString);

        // Set data awal ke EditText
        etEditName.setText(currentName != null ? currentName : "");
        etEditPhoneNumber.setText(currentPhone != null ? currentPhone : "");

        // Set gambar awal
        if (currentImageUriString != null) {
            try {
                selectedImageUri = Uri.parse(currentImageUriString); // Simpan URI awal
                Glide.with(this)
                        .load(selectedImageUri)
                        .placeholder(R.drawable.ic_default_profile)
                        .error(R.drawable.ic_default_profile) // Gambar jika URI invalid
                        .into(ivEditProfilePic);
                Log.d(TAG, "Loaded initial image from URI: " + selectedImageUri.toString());
            } catch (Exception e) {
                Log.e(TAG, "Error parsing initial image URI: " + currentImageUriString, e);
                ivEditProfilePic.setImageResource(R.drawable.ic_default_profile); // Fallback
                selectedImageUri = null;
            }
        } else {
            ivEditProfilePic.setImageResource(R.drawable.ic_default_profile);
            Log.d(TAG, "Initial image URI is null, using default.");
        }

        // Listener untuk tombol Simpan
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangesAndReturn();
            }
        });

        // Listener untuk ImageView (atau TextView hint) untuk memilih gambar
        View.OnClickListener changePhotoListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        };
        ivEditProfilePic.setOnClickListener(changePhotoListener);
        if (tvChangePhotoHint != null) { // Jika TextView hint ada
            tvChangePhotoHint.setOnClickListener(changePhotoListener);
        }


        // Listener untuk tombol Kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Tutup activity tanpa menyimpan (hasil default = RESULT_CANCELED)
            }
        });
    }

    // Method untuk membuka galeri
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // Lebih modern & persisten
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        // Penting: tambahkan flag untuk izin baca persisten
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        pickImageLauncher.launch(Intent.createChooser(intent, getString(R.string.pilih_gambar)));
        Log.d(TAG, "Launching image picker intent.");
    }

    // Method untuk menyimpan perubahan dan kembali ke MainActivity
    private void saveChangesAndReturn() {
        String newName = etEditName.getText().toString().trim();
        String newPhone = etEditPhoneNumber.getText().toString().trim();

        // Validasi sederhana (opsional)
        if (newName.isEmpty()) {
            etEditName.setError("Nama tidak boleh kosong");
            etEditName.requestFocus();
            return;
        }

        if (newPhone.isEmpty()) {
            etEditPhoneNumber.setError("Nomor tidak boleh kosong");
            etEditPhoneNumber.requestFocus();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_NAME, newName);
        resultIntent.putExtra(EXTRA_PHONE, newPhone);
        if (selectedImageUri != null) {
            resultIntent.putExtra(EXTRA_IMAGE_URI, selectedImageUri.toString());
            Log.d(TAG, "Returning image URI: " + selectedImageUri.toString());
        } else {
            // Jika tidak ada gambar dipilih ATAU gambar dihapus, kirim null
            resultIntent.putExtra(EXTRA_IMAGE_URI, (String) null);
            Log.d(TAG, "Returning null image URI.");
        }

        setResult(Activity.RESULT_OK, resultIntent); // Set hasil OK dengan data
        Log.d(TAG, "Setting result OK and finishing. Name=" + newName + ", Phone=" + newPhone);
        finish(); // Tutup activity dan kembali ke MainActivity
    }
}