package com.example.instagram.fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.instagram.R;
import com.example.instagram.UserPost;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class FeedUpFragment extends Fragment {

    private ImageView uploadFeed;
    private Uri selectedImageUri;

    // List kosong untuk menyimpan objek UserPost
    private ArrayList<UserPost> userPosts = new ArrayList<>();

    // Launcher untuk memilih gambar dari galeri
    private final ActivityResultLauncher<String> selectImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    // Mengambil gambar dan membuatnya menjadi persegi
                    Bitmap bitmap = loadImageFromUri(uri);
                    if (bitmap != null) {
                        Bitmap squareBitmap = getSquareBitmap(bitmap);
                        uploadFeed.setImageBitmap(squareBitmap);
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uploadFeed = view.findViewById(R.id.uploadFeed);
        EditText etCaption = view.findViewById(R.id.et_name);
        AppCompatButton btnSubmit = view.findViewById(R.id.btn_submit);

        // Ketika ImageView ditekan → buka galeri dan pilih gambar
        uploadFeed.setOnClickListener(v -> {
            selectImageLauncher.launch("image/*"); // Membuka galeri untuk memilih gambar
        });

        // Load data dari SharedPreferences
        loadUserPosts();

        // Submit button
        btnSubmit.setOnClickListener(v -> {
            String caption = etCaption.getText().toString().trim();
            if (selectedImageUri == null) {
                Toast.makeText(getContext(), "Pilih gambar dulu ya~", Toast.LENGTH_SHORT).show();
                return;
            }

            int likes = new Random().nextInt(1000) + 1;
            int comments = new Random().nextInt(1000) + 1;
            String currentTime = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(new Date());

            UserPost post = new UserPost(likes, comments, caption, currentTime, selectedImageUri.toString());

            // Simpan post ke dalam list
            userPosts.add(post); // Menambah post ke dalam list

            // Simpan ke SharedPreferences
            saveUserPosts();

            Toast.makeText(getContext(), "Upload berhasil!", Toast.LENGTH_SHORT).show();

            // Reset tampilan
            etCaption.setText("");
            uploadFeed.setImageResource(R.drawable.ic_launcher_background);
            selectedImageUri = null;
        });
    }

    // Fungsi untuk memuat gambar dari URI
    private Bitmap loadImageFromUri(Uri uri) {
        try {
            return BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uri));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fungsi untuk membuat gambar menjadi persegi
    private Bitmap getSquareBitmap(Bitmap original) {
        int width = original.getWidth();
        int height = original.getHeight();
        int size = Math.min(width, height); // Menentukan sisi terkecil untuk menjadi persegi

        Bitmap squareBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new android.graphics.RectF(0, 0, width, height), new android.graphics.RectF(0, 0, size, size), Matrix.ScaleToFit.CENTER);

        // Crop dan scale gambar menjadi persegi
        squareBitmap = Bitmap.createBitmap(original, 0, 0, size, size, matrix, true);
        return squareBitmap;
    }

    // Fungsi untuk menyimpan data UserPost ke SharedPreferences tanpa JSON
    private void saveUserPosts() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPosts", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Menggunakan loop untuk menyimpan setiap post
        for (int i = 0; i < userPosts.size(); i++) {
            UserPost post = userPosts.get(i);

            // Mengonversi atribut UserPost menjadi string
            String postString = post.getLikes() + "," + post.getComments() + "," + post.getCaption() + "," + post.getTime() + "," + post.getFeedUris();

            // Menyimpan data menggunakan key unik untuk setiap post
            editor.putString("post_" + i, postString);
        }

        // Menyimpan perubahan
        editor.apply();
    }

    // Fungsi untuk memuat data UserPost dari SharedPreferences
    private void loadUserPosts() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPosts", getContext().MODE_PRIVATE);

        // Memuat setiap post dari SharedPreferences
        for (int i = 0; ; i++) {
            String postString = sharedPreferences.getString("post_" + i, null);
            if (postString == null) {
                break; // Jika tidak ada data lagi, berhenti
            }

            // Mengonversi string menjadi objek UserPost
            String[] postData = postString.split(",");
            int likes = Integer.parseInt(postData[0]);
            int comments = Integer.parseInt(postData[1]);
            String caption = postData[2];
            String time = postData[3];
            String imageUri = postData[4];

            UserPost post = new UserPost(likes, comments, caption, time, imageUri);
            userPosts.add(post); // Menambahkan ke list userPosts
        }
    }
}
