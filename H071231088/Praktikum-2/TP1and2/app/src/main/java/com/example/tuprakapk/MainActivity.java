package com.example.tuprakapk;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private ImageView imgProfile, imgHome;
    private TextView tvNama, tvBio, tvInfo;
    private Button btnEditProfile;

    private TextView tabPost, tabReplies, tabMedia;
    private View underlineblue;

    private User user;

    private final ActivityResultLauncher<Intent> editProfileLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    if (data.hasExtra("user")) {
                        user = data.getParcelableExtra("user");
                        // Simpan data default yang diperbarui
                        User.saveDefaultUser(this, user);
                        updateUIFromUser(user);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgProfile = findViewById(R.id.imgProfile);
        imgHome = findViewById(R.id.imghome);
        tvNama = findViewById(R.id.tvNama);
        tvBio = findViewById(R.id.tvBio);
        tvInfo = findViewById(R.id.tvInfo);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        tabPost = findViewById(R.id.tabPost);
        tabReplies = findViewById(R.id.tabReplies);
        tabMedia = findViewById(R.id.tabMedia);

        underlineblue = findViewById(R.id.underlineblue);

        // Ambil data user dari intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            user = intent.getParcelableExtra("user");
            updateUIFromUser(user);
        } else {
            // Jika tidak ada data dari intent, buat User default dari SharedPreferences
            user = User.createDefaultUser(this);
            updateUIFromUser(user);
        }

        btnEditProfile.setOnClickListener(v -> {
            Intent intentEdit = new Intent(MainActivity.this, EditProfileActivity.class);
            if (user != null) {
                intentEdit.putExtra("user", user);
                editProfileLauncher.launch(intentEdit);
            }
        });

        setupTabs();
    }

    private void updateUIFromUser(User user) {
        if (user != null) {
            tvNama.setText(user.getName());
            tvBio.setText(user.getBio());

            if (user.getImageProfileUri() != null && !user.getImageProfileUri().isEmpty()) {
                try {
                    imgProfile.setImageURI(Uri.parse(user.getImageProfileUri()));
                } catch (Exception e) {
                    e.printStackTrace(); // debug kalau error
//                    imgProfile.setImageResource(User.getDefaultProfileImageResId());
                }
            }
//            else {
//                imgProfile.setImageResource(User.getDefaultProfileImageResId());
//            }

            if (user.getImageHomeUri() != null && !user.getImageHomeUri().isEmpty()) {
                try {
                    imgHome.setImageURI(Uri.parse(user.getImageHomeUri()));
                } catch (Exception e) {
                    e.printStackTrace(); // debug kalau error
//                    imgHome.setImageResource(R.drawable.pp);
                }
            }
//            else {
//                imgHome.setImageResource(R.drawable.pp);
//            }

            tvInfo.setText(buildInfoText(user));
        }
    }

    private SpannableStringBuilder buildInfoText(User user) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        if (user.getLokasi() != null && !user.getLokasi().isEmpty()) {
            builder.append(getImageSpan(R.drawable.baseline_add_location_24));
            builder.append(" ").append(user.getLokasi());
        }

        if (user.getWeb() != null && !user.getWeb().isEmpty()) {
            if (builder.length() > 0) builder.append("  ");
            builder.append(getImageSpan(R.drawable.baseline_add_link_24));
            builder.append(" ").append(user.getWeb());
        }

        if (user.getTanggal() != null && !user.getTanggal().isEmpty()) {
            if (builder.length() > 0) builder.append("  ");
            builder.append(getImageSpan(R.drawable.baseline_celebration_24));
            builder.append(" ").append(user.getTanggal());
        }

        return builder;
    }

    private SpannableString getImageSpan(int drawableResId) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableResId);
        if (drawable != null) {
            int size = (int) (tvInfo.getTextSize() * 1.2);
            drawable.setBounds(0, 0, size, size);
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            SpannableString spannable = new SpannableString(" ");
            spannable.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannable;
        } else {
            return new SpannableString("");
        }
    }

    private void setupTabs() {
        View.OnClickListener tabClickListener = v -> {
            TextView clickedTab = (TextView) v;
            setTabActive(clickedTab);
            moveUnderline(clickedTab);
        };

        tabPost.setOnClickListener(tabClickListener);
        tabReplies.setOnClickListener(tabClickListener);
        tabMedia.setOnClickListener(tabClickListener);

        // Set tab Post as default active
        tabPost.post(() -> {
            setTabActive(tabPost);
            moveUnderline(tabPost);
        });
    }

    private void setTabActive(TextView tab) {
        resetTabs();
        tab.setTypeface(null, Typeface.BOLD);
        tab.setTextColor(Color.WHITE);
    }

    private void resetTabs() {
        int defaultColor = Color.parseColor("#71767B");
        tabPost.setTypeface(null, Typeface.NORMAL);
        tabPost.setTextColor(defaultColor);
        tabReplies.setTypeface(null, Typeface.NORMAL);
        tabReplies.setTextColor(defaultColor);
        tabMedia.setTypeface(null, Typeface.NORMAL);
        tabMedia.setTextColor(defaultColor);
    }

    private void moveUnderline(TextView tab) {
        float scale = getResources().getDisplayMetrics().density;
        int fixedWidth = (int) (60 * scale + 0.5f);
        underlineblue.getLayoutParams().width = fixedWidth;
        underlineblue.requestLayout();

        float tabCenterX = tab.getX() + (tab.getWidth() - fixedWidth) / 2f;
        underlineblue.animate().x(tabCenterX).setDuration(200).start();
    }
}