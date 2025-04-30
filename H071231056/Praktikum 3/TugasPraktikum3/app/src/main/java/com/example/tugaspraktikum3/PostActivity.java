package com.example.tugaspraktikum3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "PostActivity";

    private ImageView profileImageView, postImagePreview;
    private TextView profileNameTextView, noImageText;
    private Button selectImageButton, postButton;
    private EditText captionEditText;

    private String usernamePersonal;
    private User personalUser;
    private Uri selectedImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.postActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // INISIALISASI VIEW
        profileImageView = findViewById(R.id.profileImageView);
        postImagePreview = findViewById(R.id.postImagePreview);
        profileNameTextView = findViewById(R.id.profileNameTextView);
        noImageText = findViewById(R.id.noImageText);
        selectImageButton = findViewById(R.id.selectImageButton);
        postButton = findViewById(R.id.postButton);
        captionEditText = findViewById(R.id.captionEditText);

        postButton.setEnabled(false);


        // AMBIL USERNAME DARI INTENT
        usernamePersonal = getIntent().getStringExtra("USERNAME");

        if (usernamePersonal == null || usernamePersonal.isEmpty()) {
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // CARI USER PERSONAL DARI DATA DUMMY
        for (User user : DataDummy.getDummyUsers()) {
            if (user.getUsername().equals(usernamePersonal)) {
                personalUser = user;
                break;
            }
        }

        if (personalUser == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Tampilkan Username
        profileNameTextView.setText(personalUser.getUsername());

        // Tombol Select Image
        selectImageButton.setOnClickListener(v -> openGallery());

        // Tombol Post
        postButton.setOnClickListener(v -> postFeed());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        // This flag is crucial for persistable permissions
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            if (selectedImageUri != null) {
                try {
                    // Take persistent permissions right when we get the URI
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    getContentResolver().takePersistableUriPermission(
                            selectedImageUri,
                            takeFlags
                    );

                    Log.d(TAG, "Successfully obtained persistable permission for URI: " + selectedImageUri);

                    if (postImagePreview != null) {
                        postImagePreview.setImageURI(selectedImageUri);
                        postImagePreview.setVisibility(ImageView.VISIBLE);
                        noImageText.setVisibility(TextView.GONE);
                    } else {
                        Log.e(TAG, "postImagePreview is null, cannot set image.");
                    }

                    postButton.setEnabled(true);
                } catch (SecurityException e) {
                    Log.e(TAG, "Failed to take persistable permission for URI: " + selectedImageUri, e);
                    Toast.makeText(this, "Cannot access this image. Please select another.", Toast.LENGTH_SHORT).show();
                    selectedImageUri = null;
                    postButton.setEnabled(false);
                }
            }
        }
    }


    private void postFeed() {
        String caption = captionEditText.getText().toString().trim();

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
            return;
        }

        if (caption.isEmpty()) {
            caption = "No caption";
        }

        // BUAT FEED BARU
        User.Feed newFeed = new User.Feed(selectedImageUri, caption, personalUser.getUsername());
        // Permission already taken, no need to call saveImageUri
        Log.d(TAG, "Creating new feed with URI: " + selectedImageUri);
        Log.d(TAG, "URI exists: " + (selectedImageUri != null));
        Log.d(TAG, "URI scheme: " + (selectedImageUri != null ? selectedImageUri.getScheme() : "null"));

        // Tambahkan ke feeds user personal
        personalUser.getFeeds().add(0, newFeed); // Add at index 0 to show it first
        System.out.println("Cek apakaha shjkafhbewhrw fhsjdhdha");

        // Create intent for ProfileActivity
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        profileIntent.putExtra("USERNAME", personalUser.getUsername());
        profileIntent.putExtra("REFRESH_FEED", true);

        Log.d(TAG, "User now has " + personalUser.getFeeds().size() + " feeds");
        Log.d(TAG, "First feed has URI: " + (personalUser.getFeeds().get(0).hasImageUri() ?
                personalUser.getFeeds().get(0).getImageUri() : "No URI"));


        // Also create intent for MainActivity to refresh when it resumes
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra("REFRESH_FEED", true);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Start MainActivity first (it will be in the background)
        startActivity(mainIntent);

        // Then start ProfileActivity (it will be in the foreground)
        startActivity(profileIntent);

        Toast.makeText(this, "Post successful!", Toast.LENGTH_SHORT).show();
        finish();
    }
}