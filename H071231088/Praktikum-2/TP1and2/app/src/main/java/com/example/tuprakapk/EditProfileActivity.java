package com.example.tuprakapk;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.Manifest;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView imgProfile, imgHome;
    private TextInputEditText etName, etBio, etLokasi, etWeb, etTanggal;
    private Button btnSimpan;
    private Calendar calendar;

    private User user;

    private Uri imageProfileUri, imageHomeUri;

    private final ActivityResultLauncher<Intent> pickImageProfile = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageProfileUri = result.getData().getData();
                    displayImage(imgProfile, imageProfileUri);
                    if (user != null) {
                        user.setImageProfileUri(imageProfileUri != null ? imageProfileUri.toString() : null);
                    }
                }
            });

    private final ActivityResultLauncher<Intent> pickImageHome = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageHomeUri = result.getData().getData();
                    displayImage(imgHome, imageHomeUri);
                    if (user != null) {
                        user.setImageHomeUri(imageHomeUri != null ? imageHomeUri.toString() : null);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        imgProfile = findViewById(R.id.imgProfile);
        imgHome = findViewById(R.id.imgHome);
        etName = findViewById(R.id.etName);
        etBio = findViewById(R.id.etBio);
        etLokasi = findViewById(R.id.etLokasi);
        etWeb = findViewById(R.id.etWeb);
        etTanggal = findViewById(R.id.etTanggal);
        btnSimpan = findViewById(R.id.btnSimpan);
        calendar = Calendar.getInstance();

        checkPermissions(); // Tambahan: Cek izin akses

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            user = intent.getParcelableExtra("user");
            if (user != null) {
                etName.setText(user.getName());
                etBio.setText(user.getBio());
                etLokasi.setText(user.getLokasi());
                etWeb.setText(user.getWeb());
                etTanggal.setText(user.getTanggal());

                imageProfileUri = user.getImageProfileUri() != null ? Uri.parse(user.getImageProfileUri()) : null;
                imageHomeUri = user.getImageHomeUri() != null ? Uri.parse(user.getImageHomeUri()) : null;

                displayImage(imgProfile, imageProfileUri);
                displayImage(imgHome, imageHomeUri);
            }
        }

        imgProfile.setOnClickListener(v -> {
            Intent intentProfile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intentProfile.setType("image/*");
            pickImageProfile.launch(Intent.createChooser(intentProfile, "Pilih Gambar Profil"));
        });

        imgHome.setOnClickListener(v -> {
            Intent intentHome = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intentHome.setType("image/*");
            pickImageHome.launch(Intent.createChooser(intentHome, "Pilih Gambar Header"));
        });

        etTanggal.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        etTanggal.setText(formattedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        btnSimpan.setOnClickListener(v -> {
            if (user != null) {
                user.setName(etName.getText() != null ? etName.getText().toString() : "");
                user.setBio(etBio.getText() != null ? etBio.getText().toString() : "");
                user.setLokasi(etLokasi.getText() != null ? etLokasi.getText().toString() : "");
                user.setWeb(etWeb.getText() != null ? etWeb.getText().toString() : "");
                user.setTanggal(etTanggal.getText() != null ? etTanggal.getText().toString() : "");
                user.setImageProfileUri(imageProfileUri != null ? imageProfileUri.toString() : null);
                user.setImageHomeUri(imageHomeUri != null ? imageHomeUri.toString() : null);

                User.saveDefaultUser(this, user);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("user", user);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    // ✅ Tampilkan gambar dengan aman
    private void displayImage(ImageView imageView, Uri imageUri) {
        if (imageUri != null) {
            try {
                Bitmap bitmap;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imageUri);
                    bitmap = ImageDecoder.decodeBitmap(source);
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                }
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ✅ Cek dan request permission
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 100);
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
            }
        }
    }

    // Opsional: handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode == 100 || requestCode == 101) &&
                (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
            // Izin ditolak
            // Anda bisa tampilkan Toast atau Dialog di sini
        }
    }
}
