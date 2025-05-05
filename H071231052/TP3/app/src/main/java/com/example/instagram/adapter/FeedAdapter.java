package com.example.instagram.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.FeedRepository;
import com.example.instagram.NumberFormatter;
import com.example.instagram.ProfileActivity;
import com.example.instagram.ProfilePribadiActivity;
import com.example.instagram.R;
import com.example.instagram.model.FeedModel;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private final Context context;
    private final List<FeedModel> feedList;
    private final int maxCaptionWidthPx;

    public FeedAdapter(Context context, List<FeedModel> feedList) {
        this.context = context;
        this.feedList = feedList;
        this.maxCaptionWidthPx = dpToPx(360);
    }

    public FeedAdapter(Context context, FeedModel feed){
        this.context = context;
        this.feedList = new ArrayList<>();
        this.feedList.add(feed);
        this.maxCaptionWidthPx = dpToPx(360);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        FeedModel item = feedList.get(position);
        FeedRepository.setFeedList(feedList);

        if (item.getProfileImageUrl() != null) {
            Glide.with(context)
                    .load(item.getProfileImageUrl())
                    .into(holder.profileImage);
        } else {
            holder.profileImage.setImageResource(item.getProfileImage());
        }


        holder.profileImage.setImageResource(item.getProfileImage());
        holder.username.setText(item.getUsername());

        setupCaption(holder, item);


        holder.username.setOnClickListener(v -> openProfileActivity(context, item.getUsername()));
        holder.profileImage.setOnClickListener(v -> openProfileActivity(context, item.getUsername()));

        holder.likes.setText(NumberFormatter.formatNumber(item.getLikes()));
        holder.comments.setText(NumberFormatter.formatNumber(item.getComments()));

        setupPhotos(holder, item);

        setupLikeButton(holder, item);
        setupSaveButton(holder, item);
    }

    private void openProfileActivity(Context context, String username) {

        if (username.equals("ys_erlanggaa")) {
            Intent intent = new Intent(context, ProfilePribadiActivity.class);
            intent.putParcelableArrayListExtra("feedList", new ArrayList<>(feedList));
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("username", username);
            intent.putParcelableArrayListExtra("feedList", new ArrayList<>(feedList));
            context.startActivity(intent);
        }
    }

    private void setupCaption(FeedViewHolder holder, FeedModel item) {
        if (item.getCaption().isEmpty()) {
            holder.bagianCaption.setVisibility(View.GONE);
            return;
        }

        holder.bagianCaption.setVisibility(View.VISIBLE);

        String fullCaption = item.getUsername() + " " + item.getCaption();
        SpannableString spannableCaption = new SpannableString(fullCaption);
        spannableCaption.setSpan(new StyleSpan(Typeface.BOLD), 0, item.getUsername().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.caption.setText(spannableCaption);
        holder.caption.setMaxLines(1);
        holder.tvShowMore.setVisibility(View.VISIBLE);

        holder.caption.post(() -> {
            float textWidth = holder.caption.getPaint().measureText(fullCaption);
            int finalWidth = (int) Math.min(textWidth + holder.caption.getPaddingStart() + holder.caption.getPaddingEnd(), maxCaptionWidthPx);
            ViewGroup.LayoutParams params = holder.caption.getLayoutParams();
            params.width = finalWidth;
            holder.caption.setLayoutParams(params);
        });

        holder.tvShowMore.setOnClickListener(v -> {
            holder.caption.setMaxLines(Integer.MAX_VALUE);
            holder.tvShowMore.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = holder.caption.getLayoutParams();
            params.width = maxCaptionWidthPx;
            holder.caption.setLayoutParams(params);
        });
    }

    private void setupPhotos(FeedViewHolder holder, FeedModel item) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerViewPhotos.setLayoutManager(layoutManager);
        holder.recyclerViewPhotos.setAdapter(new PhotoAdapter(context, item.getPhotoList()));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        if (holder.recyclerViewPhotos.getOnFlingListener() == null) {
            snapHelper.attachToRecyclerView(holder.recyclerViewPhotos);
        }

        if (item.getPhotoList().size() > 1) {
            setupIndicators(holder.indicatorLayout, item.getPhotoList().size(), 0);
        } else {
            holder.indicatorLayout.removeAllViews();
        }

        holder.recyclerViewPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visiblePosition = layoutManager.findFirstVisibleItemPosition();
                setupIndicators(holder.indicatorLayout, item.getPhotoList().size(), visiblePosition);
            }
        });

        GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                performLike(holder, item);
                animateHeart(holder.ivHeartAnimation);
                return true;
            }
        });

        holder.recyclerViewPhotos.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void setupLikeButton(FeedViewHolder holder, FeedModel item) {
        holder.iv_like.setImageResource(item.isLiked() ? R.drawable.ic_like_active : R.drawable.ic_like_unactive);

        holder.iv_like.setOnClickListener(v -> {
            performLike(holder, item);
        });
    }

    private void setupSaveButton(FeedViewHolder holder, FeedModel item) {
        holder.iv_saved.setImageResource(item.isSaved() ? R.drawable.ic_saved_active : R.drawable.ic_saved_unactive);
        holder.iv_saved.setOnClickListener(v -> {
            item.setSaved(!item.isSaved());
            holder.iv_saved.setImageResource(item.isSaved() ? R.drawable.ic_saved_active : R.drawable.ic_saved_unactive);
            animateBounce(holder.iv_saved);
        });
    }

    private void performLike(FeedViewHolder holder, FeedModel item) {
        boolean newLikeState = !item.isLiked();
        item.setLiked(newLikeState);
        int newLikeCount = item.getLikes() + (newLikeState ? 1 : -1);
        item.setLikes(newLikeCount);

        holder.iv_like.setImageResource(newLikeState ? R.drawable.ic_like_active : R.drawable.ic_like_unactive);
        animateBounce(holder.iv_like);

        new Handler().postDelayed(() -> {
            holder.likes.setText(NumberFormatter.formatNumber(newLikeCount));
        }, 200);
    }

    private void animateHeart(ImageView heartView) {
        heartView.setVisibility(View.VISIBLE);
        heartView.setScaleX(0f);
        heartView.setScaleY(0f);
        heartView.setAlpha(1f);

        heartView.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(200)
                .withEndAction(() -> heartView.animate()
                        .alpha(0f)
                        .setDuration(400)
                        .withEndAction(() -> heartView.setVisibility(View.GONE))
                        .start())
                .start();
    }

    private void animateBounce(View view) {
        Animation bounce = AnimationUtils.loadAnimation(context, R.anim.bounce);
        view.startAnimation(bounce);
    }

    private void setupIndicators(LinearLayout indicatorLayout, int count, int currentIndex) {
        indicatorLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(context);
            boolean isActive = i == currentIndex;

            dot.setImageDrawable(ContextCompat.getDrawable(context,
                    isActive ? R.drawable.dot_active : R.drawable.dot_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(8), dpToPx(8));
            params.setMargins(dpToPx(2), 0, dpToPx(2), dpToPx(2));
            dot.setLayoutParams(params);

            indicatorLayout.addView(dot);
            if (isActive) animateDotBounce(dot);
        }
    }

    private void animateDotBounce(ImageView dot) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(dot, View.SCALE_X, 1.4f, 1.4f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(dot, View.SCALE_Y, 1.4f, 1.4f);
        scaleX.setInterpolator(new OvershootInterpolator());
        scaleY.setInterpolator(new OvershootInterpolator());
        scaleX.setDuration(300);
        scaleY.setDuration(300);
        scaleX.start();
        scaleY.start();
    }

    private int dpToPx(int dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage, iv_like, iv_saved, ivHeartAnimation;
        TextView username, caption, likes, comments, tvShowMore;
        RecyclerView recyclerViewPhotos;
        LinearLayout bagianCaption, indicatorLayout;


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            username = itemView.findViewById(R.id.tvUsername);
            caption = itemView.findViewById(R.id.tvCaption);
            bagianCaption = itemView.findViewById(R.id.bagianCaption);
            likes = itemView.findViewById(R.id.tvLikes);
            comments = itemView.findViewById(R.id.tvComments);
            recyclerViewPhotos = itemView.findViewById(R.id.recyclerViewPhotos);
            indicatorLayout = itemView.findViewById(R.id.indicatorLayout);
            iv_like = itemView.findViewById(R.id.tvLikesIcon);
            iv_saved = itemView.findViewById(R.id.iv_save);
            tvShowMore = itemView.findViewById(R.id.tvShowMore);
            ivHeartAnimation = itemView.findViewById(R.id.ivHeartAnimation);
        }
    }
}
