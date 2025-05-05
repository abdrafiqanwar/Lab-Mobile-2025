package com.example.praktikum3.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.praktikum3.R;

public class StoryDetailActivity extends AppCompatActivity {

    private ImageView ivStoryDetail;
    private TextView tvStoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        ivStoryDetail = findViewById(R.id.iv_story_detail);
        tvStoryTitle = findViewById(R.id.tv_story_title);

        String title = getIntent().getStringExtra("story_title");
        int imageRes = getIntent().getIntExtra("story_image", R.drawable.story01);

        ivStoryDetail.setImageResource(imageRes);
        tvStoryTitle.setText(title);
    }
}

