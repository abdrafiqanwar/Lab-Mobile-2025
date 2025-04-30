package com.example.tugaspraktikum3;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class FeedDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feed_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.feedDetailLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // DAPATKAN DATA DARI INTENT
        String captions = getIntent().getStringExtra("CAPTIONS");
        String imageUriString = getIntent().getStringExtra("IMAGE_URI");
        int imageResId = getIntent().getIntExtra("IMAGE_RES_ID", -1);

        // SET IMAGE
        ImageView imageFeedDetail = findViewById(R.id.imageFeedDetail);
        if (imageUriString != null && !imageUriString.isEmpty()) {
            Log.d("FeedDetailActivity", "Loading image from URI: " + imageUriString);
            Uri imageUri = Uri.parse(imageUriString);

            // Use Glide for better URI handling
            Glide.with(this)
                    .load(imageUri)
                    .placeholder(R.drawable.cheese)
                    .error(R.drawable.bbq_sauce)
                    .into(imageFeedDetail);
        } else if (imageResId != -1) {
            Log.d("FeedDetailActivity", "Loading image from resource: " + imageResId);
            imageFeedDetail.setImageResource(imageResId);
        }

        TextView textFeedDetail = findViewById(R.id.textFeedDetail);
        textFeedDetail.setText(captions);

    }
}