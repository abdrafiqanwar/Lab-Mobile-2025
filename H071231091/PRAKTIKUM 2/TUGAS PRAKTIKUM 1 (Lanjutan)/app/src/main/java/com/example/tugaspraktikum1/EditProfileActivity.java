package com.example.tugaspraktikum1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;  // Untuk menyimpan URI gambar yang dipilih


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText editName = findViewById(R.id.editName);
        EditText editPhone = findViewById(R.id.editPhone);
        ImageView editProfileImage = findViewById(R.id.editProfileImage);

        // Ambil data dari mainactivity
        editName.setText(getIntent().getStringExtra("name"));
        editPhone.setText(getIntent().getStringExtra("phone"));

        String imageUriString = getIntent().getStringExtra("imageUri");
        if (imageUriString != null && !imageUriString.isEmpty()) {
            selectedImageUri = Uri.parse(imageUriString);
            try {
                editProfileImage.setImageURI(selectedImageUri);
            } catch (SecurityException e) {
                editProfileImage.setImageResource(R.drawable.mlluoyii);
                selectedImageUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.mlluoyii);
            }
        }

        editProfileImage.setOnClickListener(v -> openImagePicker());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);//izinnya
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// Jika hasilnya sukses dan data ada, maka ambil gambar yang dipilih
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            getContentResolver().takePersistableUriPermission(
                    selectedImageUri,//izin
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            ImageView editProfileImage = findViewById(R.id.editProfileImage);
            editProfileImage.setImageURI(selectedImageUri);
        }
    }

    public void onSaveClick(View view) {
        EditText editName = findViewById(R.id.editName);
        EditText editPhone = findViewById(R.id.editPhone);

        String name = editName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pastikan kita memiliki URI gambar yang valid
        Uri finalImageUri = (selectedImageUri != null) ? selectedImageUri :
                Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.mlluoyii);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("phone", phone);
        resultIntent.putExtra("imageUri", finalImageUri.toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}