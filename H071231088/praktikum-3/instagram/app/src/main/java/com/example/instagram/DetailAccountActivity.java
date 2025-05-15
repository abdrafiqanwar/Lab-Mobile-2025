package com.example.instagram;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailAccountActivity extends AppCompatActivity {

    private TextView username, bio, countPost, countFollower, countFollowing;
    private CircleImageView dpProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_account);  // Ganti dengan layout yang sesuai

        // Inisialisasi Views
        username = findViewById(R.id.dp_username);
        bio = findViewById(R.id.bio);
        countPost = findViewById(R.id.countPost);
        countFollower = findViewById(R.id.countFollower);
        countFollowing = findViewById(R.id.countFollowing);
        dpProfile = findViewById(R.id.dp_profile);

        // Ambil data dari Intent
        String userName = getIntent().getStringExtra("username");

        if (userName != null) {
            // Mencari data pengguna berdasarkan nama dari DataSource
            Post userPost = findUserPost(userName);

            // Set data pada TextView dan gambar profil
            if (userPost != null) {
                username.setText(userPost.getName());
                bio.setText(userPost.getBio());
                countPost.setText(String.valueOf(userPost.getTotalPosts()));
                countFollower.setText(String.valueOf(userPost.getFollowers()));
                countFollowing.setText(String.valueOf(userPost.getFollowing()));
                dpProfile.setImageResource(userPost.getImage());
            }
        }
    }

    private Post findUserPost(String username) {
        for (Post post : DataSource.posts) {
            if (post.getName().equals(username)) {
                return post;
            }
        }
        return null;
    }
}
