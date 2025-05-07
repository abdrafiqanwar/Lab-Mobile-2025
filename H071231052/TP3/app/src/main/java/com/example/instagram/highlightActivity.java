package com.example.instagram;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.instagram.adapter.highlightPagerAdapter;
import com.example.instagram.model.PhotoModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class highlightActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private highlightPagerAdapter adapter;
    private ArrayList<PhotoModel> photos;
    private LinearLayout progressContainer;
    private Handler handler;
    private Runnable storyRunnable;
    private int currentIndex = 0;
    private boolean isPaused = false;

    TextView highlightUsername;
    CircleImageView highlightProfile;

    private final int DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlight);

        viewPager = findViewById(R.id.viewPagerHighlight);
        progressContainer = findViewById(R.id.progressContainer);

        photos = getIntent().getParcelableArrayListExtra("photos");
        if (photos == null) {
            photos = new ArrayList<>(); // biar gak null
        }

        adapter = new highlightPagerAdapter(this, photos);
        viewPager.setAdapter(adapter);

        setupProgressBars(photos.size());


        highlightUsername = findViewById(R.id.highlightUsername);
        highlightProfile = findViewById(R.id.highlightProfile);

        highlightUsername.setText(getIntent().getStringExtra("username"));
        if (getIntent().getStringExtra("profileImage") != null) {
            Glide.with(this)
                    .load(getIntent().getStringExtra("profileImage"))
                    .into(highlightProfile);
        } else {
            highlightProfile.setImageResource(getIntent().getIntExtra("profileImage", 0));
        }
        handler = new Handler();
        startAutoScroll();

        // Tap navigation
        findViewById(R.id.tapLeft).setOnClickListener(v -> {
            pauseAndResume();
            if (currentIndex > 0) {
                currentIndex--;
                viewPager.setCurrentItem(currentIndex, true);
                resetProgress(currentIndex);
            }
        });

        findViewById(R.id.tapRight).setOnClickListener(v -> {
            pauseAndResume();
            if (currentIndex < photos.size() - 1) {
                currentIndex++;
                viewPager.setCurrentItem(currentIndex, true);
                resetProgress(currentIndex);
            } else {
                finish();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentIndex = position;
                pauseAndResume();
                resetProgress(currentIndex);
            }
        });
    }

    private List<ProgressBar> progressBars = new ArrayList<>();
    private int currentPosition = 0;

    private void setupProgressBars(int count) {
        LinearLayout container = findViewById(R.id.progressContainer);
        container.removeAllViews();
        progressBars.clear();

        for (int i = 0; i < count; i++) {
            ProgressBar bar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            params.setMarginEnd(8);
            bar.setLayoutParams(params);
            bar.setMax(1000);
            bar.setProgress(0);
            bar.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.progress_drawable)); // custom drawable (optional)
            container.addView(bar);
            progressBars.add(bar);
        }

        animateProgress(currentPosition);
    }

    private void animateProgress(int index) {
        if (index >= progressBars.size()) return;

        ProgressBar bar = progressBars.get(index);
        ObjectAnimator animator = ObjectAnimator.ofInt(bar, "progress", 0, 1000);
        animator.setDuration(DURATION);
        animator.setInterpolator(new LinearInterpolator());

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (currentPosition < progressBars.size() - 1) {
                    currentPosition++;
                    viewPager.setCurrentItem(currentPosition, true);
                } else {
                    finish();
                }
            }
        });

        animator.start();
    }

    private void startAutoScroll() {
        storyRunnable = new Runnable() {
            int progress = 0;

            @Override
            public void run() {
                if (!isPaused) {
                    ProgressBar bar = (ProgressBar) progressContainer.getChildAt(currentIndex);
                    bar.setProgress(progress);
                    progress += 50;
                    if (progress <= 1000) {
                        handler.postDelayed(this, DURATION / 20);
                    } else {
                        if (currentIndex < photos.size() - 1) {
                            currentIndex++;
                            viewPager.setCurrentItem(currentIndex, true);
                            progress = 0;
                            resetProgress(currentIndex);
                            handler.postDelayed(this, DURATION / 20);
                        } else {
                            finish();
                        }
                    }
                }
            }
        };
        handler.post(storyRunnable);
    }

    private void resetProgress(int index) {
        for (int i = 0; i < progressContainer.getChildCount(); i++) {
            ProgressBar bar = (ProgressBar) progressContainer.getChildAt(i);
            bar.setProgress(i < index ? 1000 : 0);
        }
        handler.removeCallbacks(storyRunnable);
        startAutoScroll();
    }

    private void pauseAndResume() {
        isPaused = true;
        handler.removeCallbacks(storyRunnable);
        new Handler().postDelayed(() -> {
            isPaused = false;
            startAutoScroll();
        }, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(storyRunnable);
    }
}
