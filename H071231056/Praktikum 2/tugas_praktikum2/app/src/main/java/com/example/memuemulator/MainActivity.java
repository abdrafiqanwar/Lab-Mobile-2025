package com.example.memuemulator;

import static com.example.memuemulator.R.drawable.profile_image;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView profileUsername, profileShortname;
    private ImageView profileImage, editImageView;
    private User userToSend; // deklarasi global

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Init views
        profileUsername = findViewById(R.id.profile_username);
        profileShortname = findViewById(R.id.profile_shortname);
        profileImage = findViewById(R.id.profile_image);
        editImageView = findViewById(R.id.edit);

        // Default email
        String email = "pangeranjuhrifar@gmail.com";

        // Ambil data dari intent
        User receivedUser = getIntent().getParcelableExtra("user");

        if (receivedUser != null) {
            // Set UI
            profileUsername.setText(receivedUser.getName());
            profileShortname.setText(receivedUser.getUsername());

            if (receivedUser.getAvatarUri() != null) {
                profileImage.setImageURI(Uri.parse(receivedUser.getAvatarUri()));
            } else {
                profileImage.setImageResource(profile_image);
            }

            userToSend = new User(
                    receivedUser.getName(),
                    receivedUser.getUsername(),
                    email,
                    receivedUser.getAvatarUri()
            );
        } else {
            String name = profileUsername.getText().toString();
            String username = profileShortname.getText().toString();
            userToSend = new User(name, username, email, null);
        }

        // Aksi tombol edit
        editImageView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("user", userToSend);
            startActivity(intent);
        });
    }
}
