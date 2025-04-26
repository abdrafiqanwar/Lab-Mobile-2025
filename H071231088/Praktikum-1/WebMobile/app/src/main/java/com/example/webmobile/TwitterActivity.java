package com.example.webmobile;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Typeface;

public class TwitterActivity extends AppCompatActivity {

    private TextView tabPost, tabReplies, tabMedia;
    private View tabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter);

        // Inisialisasi komponen
        tabPost = findViewById(R.id.tabPost);
        tabReplies = findViewById(R.id.tabReplies);
        tabMedia = findViewById(R.id.tabMedia);
        tabIndicator = findViewById(R.id.tabIndicator);

        // Set semua tab ke bold secara default
        setDefaultTabStyle(tabPost);
        setDefaultTabStyle(tabReplies);
        setDefaultTabStyle(tabMedia);

        // Atur klik listener untuk setiap tab
        tabPost.setOnClickListener(v -> updateTabSelection(tabPost));
        tabReplies.setOnClickListener(v -> updateTabSelection(tabReplies));
        tabMedia.setOnClickListener(v -> updateTabSelection(tabMedia));

        // Set default tab ke "Post"
        tabPost.post(() -> updateTabSelection(tabPost));
    }

    private void updateTabSelection(TextView selectedTab) {
        // Reset semua tab ke gaya dasar (bold, warna abu-abu, ukuran default)
        resetTabStyle(tabPost);
        resetTabStyle(tabReplies);
        resetTabStyle(tabMedia);

        // Atur tab yang dipilih menjadi warna putih dan ukuran 20sp
        selectedTab.setTextColor(Color.WHITE);
        selectedTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        // Geser indikator ke tengah tab yang dipilih
        tabIndicator.post(() -> {
            float tabCenterX = selectedTab.getX() + (selectedTab.getWidth() / 2f);
            float indicatorWidth = tabIndicator.getWidth() / 2f;
            float newIndicatorX = tabCenterX - indicatorWidth;

            tabIndicator.animate()
                    .x(newIndicatorX)
                    .setDuration(200);
        });
    }

    private void setDefaultTabStyle(TextView tab) {
        tab.setTypeface(null, Typeface.BOLD);
        tab.setTextColor(Color.GRAY); // Warna dasar abu-abu
        tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Ukuran dasar
    }

    private void resetTabStyle(TextView tab) {
        tab.setTypeface(null, Typeface.BOLD);
        tab.setTextColor(Color.GRAY); // Warna kembali ke abu-abu
        tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Kembali ke ukuran dasar
    }
}
