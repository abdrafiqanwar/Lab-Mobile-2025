package com.example.instagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import de.hdodenhof.circleimageview.CircleImageView;

public class FeedDetailActivity extends AppCompatActivity {

    private CircleImageView ivProfile;
    private TextView nameText, timeText, likesText, commentsText, sharesText, captionText;
    private ImageView imgPost;
    private ImageButton menuButton, likeButton, commentButton, shareButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        // Inisialisasi view
        ivProfile = findViewById(R.id.iv_profile);
        nameText = findViewById(R.id.name);
        timeText = findViewById(R.id.time);
        likesText = findViewById(R.id.likes);
        commentsText = findViewById(R.id.comments);
        sharesText = findViewById(R.id.shares);
        captionText = findViewById(R.id.caption);
        imgPost = findViewById(R.id.img_post);
        menuButton = findViewById(R.id.menu_button);
        likeButton = findViewById(R.id.like_button);
        commentButton = findViewById(R.id.comment_button);
        shareButton = findViewById(R.id.share_button);
        saveButton = findViewById(R.id.save_button);

        // Periksa apakah semua view ditemukan
        if (menuButton == null || likeButton == null || commentButton == null || shareButton == null || saveButton == null) {
            Log.e("FeedDetailActivity", "One or more ImageButton views are null. Check XML IDs.");
            finish();
            return;
        }

        // Ambil data dari Intent
        Intent intent = getIntent();
        String imageUri = intent.getStringExtra("imageUri");
        String caption = intent.getStringExtra("caption");
        int likes = intent.getIntExtra("likes", 0);
        int comments = intent.getIntExtra("comments", 0);
        String time = intent.getStringExtra("time");

        // Log data untuk debugging
        Log.d("FeedDetailActivity", "imageUri: " + imageUri);
        Log.d("FeedDetailActivity", "caption: " + caption);
        Log.d("FeedDetailActivity", "time: " + time);

        // Validasi data Intent
        if (imageUri == null) {
            Log.e("FeedDetailActivity", "imageUri is null");
            finish();
            return;
        }

        // Set data ke UI
        Glide.with(this)
                .load(Uri.parse(imageUri))
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.frieren)) // Gambar fallback jika gagal
                .into(imgPost);
        Glide.with(this)
                .load(R.drawable.frieren)
                .into(ivProfile);
        nameText.setText("AbelEkaPutra.UH");
        timeText.setText(time != null ? time : "Unknown time");
        likesText.setText(String.valueOf(likes));
        commentsText.setText(String.valueOf(comments));
        sharesText.setText(String.valueOf(0));
        captionText.setText(caption != null ? caption : "");

    }
}