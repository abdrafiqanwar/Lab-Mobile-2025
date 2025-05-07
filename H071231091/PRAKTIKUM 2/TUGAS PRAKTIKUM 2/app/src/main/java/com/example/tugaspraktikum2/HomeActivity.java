package com.example.tugaspraktikum2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class HomeActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private TextInputEditText etName, etNIM;
    private Button btnNext;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgProfile = findViewById(R.id.imgProfile);
        etName = findViewById(R.id.etName);
        etNIM = findViewById(R.id.etNIM);
        btnNext = findViewById(R.id.btnNext);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukagaleri = new Intent(Intent.ACTION_GET_CONTENT);
                bukagaleri.setType("image/*");
                openGallery.launch(Intent.createChooser(bukagaleri, "Choose a picture"));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = "";
                String nim = "";

                if (etName.getText() != null) {
                    nama = etName.getText().toString();
                }

                if (etNIM.getText() != null) {
                    nim = etNIM.getText().toString();
                }

                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);

                intent.putExtra("NAMA", nama);
                intent.putExtra("NIM", nim);

                if (imageUri != null) {
                    intent.putExtra("IMAGE_URI", imageUri.toString());
                }

                startActivity(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> openGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri = data.getData();
                            imgProfile.setImageURI(imageUri);
                        }
                    }
                }
            }
    );
}
