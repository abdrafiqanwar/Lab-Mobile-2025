package com.example.instagram.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.FeedDetailActivity;
import com.example.instagram.R;
import com.example.instagram.StoryDetailActivity;
import com.example.instagram.UserPost;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private static final String PREF_NAME = "user_prefs";
    private static final String HIGHLIGHT_KEY = "highlight_uris";
    private static final String ADD_HIGHLIGHT_SHOWN_KEY = "add_highlight_shown";
    private static final String USER_POSTS_PREF = "UserPosts";

    private RecyclerView recyclerViewHighlights, recyclerViewFeed;
    private List<String> highlightUris;
    private ArrayList<UserPost> userPosts;
    private SharedPreferences sharedPreferences;

    public ProfileFragment() {
        // Constructor kosong
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inisialisasi SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Ambil highlightUris
        highlightUris = getHighlightUrisFromPreferences();
        if (!isAddHighlightShown()) {
            highlightUris.add("ADD_ITEM");
        }

        // Inisialisasi RecyclerView untuk Highlights
        recyclerViewHighlights = view.findViewById(R.id.recycler_highlights);
        recyclerViewHighlights.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHighlights.setAdapter(new HighlightAdapter());

        // Inisialisasi RecyclerView untuk Feed dengan GridLayoutManager (3 kolom)
        recyclerViewFeed = view.findViewById(R.id.recyclerViewFeed);
        recyclerViewFeed.setLayoutManager(new GridLayoutManager(getContext(), 3)); // 3 kolom
        userPosts = loadUserPosts();
        recyclerViewFeed.setAdapter(new FeedAdapter(getContext(), userPosts));
        TextView emptyFeedText = view.findViewById(R.id.empty_feed_text);
        if (userPosts.isEmpty()) {
            emptyFeedText.setVisibility(View.VISIBLE);
            recyclerViewFeed.setVisibility(View.GONE);
        } else {
            emptyFeedText.setVisibility(View.GONE);
            recyclerViewFeed.setVisibility(View.VISIBLE);
        }
        // Isi data user ke UI
        TextView nameText = view.findViewById(R.id.profile_display_name);
        nameText.setText("Abel Eka");
        TextView postCount = view.findViewById(R.id.profile_post_count);
        postCount.setText(String.valueOf(userPosts.size()));
        TextView bioText = view.findViewById(R.id.profile_bio_text);
        bioText.setText("Penyihir abadi yang mencari arti kehidupan.");

        return view;
    }

    // Adapter untuk Highlight (tetap sama)
    private class HighlightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_ADD = 0;
        private static final int TYPE_IMAGE = 1;

        @Override
        public int getItemViewType(int position) {
            return highlightUris.get(position).equals("ADD_ITEM") ? TYPE_ADD : TYPE_IMAGE;
        }

        @Override
        public int getItemCount() {
            return highlightUris.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ADD) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_highlight, parent, false);
                return new RecyclerView.ViewHolder(view) {};
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_highlight, parent, false);
                return new RecyclerView.ViewHolder(view) {};
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_ADD) {
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    pickImageLauncher.launch(intent);
                });
            } else {
                ImageView img = holder.itemView.findViewById(R.id.img_highlight);
                Glide.with(getContext()).load(Uri.parse(highlightUris.get(position))).into(img);
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(getContext(), StoryDetailActivity.class);
                    intent.putExtra("imageUri", highlightUris.get(position));
                    startActivity(intent);
                });
            }
        }
    }

    // ActivityResultLauncher untuk memilih gambar highlight (tetap sama)
    ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        getContext().getContentResolver().takePersistableUriPermission(
                                imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        addHighlightUri(imageUri.toString());
                        recyclerViewHighlights.getAdapter().notifyItemInserted(highlightUris.size() - 1);
                    }
                }
            });

    // Menambahkan URI highlight baru (tetap sama)
    private void addHighlightUri(String uri) {
        highlightUris.add(uri);
        saveHighlightUrisToPreferences();
        setAddHighlightShown(true);
    }

    // Menyimpan highlight URIs ke SharedPreferences (tetap sama)
    private void saveHighlightUrisToPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HIGHLIGHT_KEY, String.join(",", highlightUris));
        editor.apply();
    }

    // Mengambil highlight URIs dari SharedPreferences (tetap sama)
    private List<String> getHighlightUrisFromPreferences() {
        String savedUris = sharedPreferences.getString(HIGHLIGHT_KEY, "");
        List<String> uris = new ArrayList<>();
        if (!savedUris.isEmpty()) {
            for (String uri : savedUris.split(",")) {
                uris.add(uri);
            }
        }
        return uris;
    }

    // Set status apakah tombol add sudah ditampilkan (tetap sama)
    private void setAddHighlightShown(boolean shown) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ADD_HIGHLIGHT_SHOWN_KEY, shown);
        editor.apply();
    }

    // Cek apakah tombol add sudah pernah ditampilkan (tetap sama)
    private boolean isAddHighlightShown() {
        return sharedPreferences.getBoolean(ADD_HIGHLIGHT_SHOWN_KEY, false);
    }

    // Memuat feed dari SharedPreferences (tetap sama)
    private ArrayList<UserPost> loadUserPosts() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(USER_POSTS_PREF, Context.MODE_PRIVATE);
        ArrayList<UserPost> posts = new ArrayList<>();

        for (int i = 0; ; i++) {
            String postString = sharedPreferences.getString("post_" + i, null);
            if (postString == null) {
                break;
            }

            try {
                String[] postData = postString.split(",");
                if (postData.length == 5) { // Pastikan data lengkap
                    int likes = Integer.parseInt(postData[0]);
                    int comments = Integer.parseInt(postData[1]);
                    String caption = postData[2];
                    String time = postData[3];
                    String imageUri = postData[4];

                    UserPost post = new UserPost(likes, comments, caption, time, imageUri);
                    posts.add(post);
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log error tanpa crash
            }
        }
        return posts;
    }

    private class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
        private Context context;
        private ArrayList<UserPost> userPosts;

        public FeedAdapter(Context context, ArrayList<UserPost> userPosts) {
            this.context = context;
            this.userPosts = userPosts;
        }

        // ViewHolder untuk item feed
        public class FeedViewHolder extends RecyclerView.ViewHolder {
            ImageView feedImage;

            public FeedViewHolder(@NonNull View itemView) {
                super(itemView);
                feedImage = itemView.findViewById(R.id.feed_image);
            }
        }

        @NonNull
        @Override
        public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
            return new FeedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
            UserPost post = userPosts.get(position);
            // Load gambar feed menggunakan Glide
            Glide.with(context)
                    .load(Uri.parse(post.getFeedUris()))
                    .into(holder.feedImage);

            // Tambahkan klik listener untuk membuka FeedDetailActivity
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, FeedDetailActivity.class);
                intent.putExtra("imageUri", post.getFeedUris());
                intent.putExtra("caption", post.getCaption());
                intent.putExtra("likes", post.getLikes());
                intent.putExtra("comments", post.getComments());
                intent.putExtra("time", post.getTime());
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return userPosts.size();
        }
    }

}