package com.example.tp1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import androidx.appcompat.widget.PopupMenu;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private static final int EDIT_PROFILE_REQUEST = 3;
    private CircleImageView profileImageView;
    private TextView nameTextView, bioTextView, universityTextView, locationTextView, statusTextView;
    private View moreIcon;
    private String name = "Zabil Sabri Muhammad";
    private String bio = "Berusaha dalam hidup";
    private String university = "Hasanuddin University";
    private String location = "Makassar";
    private String status = "Membujang";
    private Uri profileImageUri;

    // ActivityResultLauncher for EditProfileActivity
    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    ProfileData profileData = result.getData().getParcelableExtra("profile");
                    if (profileData != null) {
                        name = profileData.getName();
                        bio = profileData.getBio();
                        status = profileData.getStatus();
                        university = profileData.getUniversity();
                        location = profileData.getLocation();
                        profileImageUri = profileData.getProfileImageUri();
                        updateProfileUI();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi views
        profileImageView = findViewById(R.id.profile);
        nameTextView = findViewById(R.id.name);
        statusTextView = findViewById(R.id.status);
        bioTextView = findViewById(R.id.slogan);
        universityTextView = findViewById(R.id.ketbuild);
        locationTextView = findViewById(R.id.ketmaps);
        moreIcon = findViewById(R.id.more_icon);

        ProfileData profileData = getIntent().getParcelableExtra("profile");
        if (profileData != null) {
            name = profileData.getName();
            status = profileData.getStatus();
            bio = profileData.getBio();
            university = profileData.getUniversity();
            location = profileData.getLocation();
            profileImageUri = profileData.getProfileImageUri();
        }
        updateProfileUI();

        moreIcon.setOnClickListener(v -> showPopupMenu(v));
    }

    private void updateProfileUI() {
        nameTextView.setText(name);
        bioTextView.setText(bio);
        statusTextView.setText(status);
        universityTextView.setText(university);
        locationTextView.setText(location);

        if (profileImageUri != null) {
            Glide.with(this)
                    .load(profileImageUri)
                    .error(R.drawable.oip)
                    .into(profileImageView);
        } else {
            profileImageView.setImageResource(R.drawable.oip);
        }
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.profile_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_edit_profile) {
                openEditProfileActivity();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void openEditProfileActivity() {
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        ProfileData profileData = new ProfileData(name, bio, status, university, location, profileImageUri);
        intent.putExtra("profile", profileData);
        editProfileLauncher.launch(intent);
    }
}
