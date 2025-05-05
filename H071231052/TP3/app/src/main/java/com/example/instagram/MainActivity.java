package com.example.instagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.adapter.FeedAdapter;
import com.example.instagram.model.FeedModel;
import com.example.instagram.model.PhotoModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<FeedModel> feedList;
    FeedAdapter adapter;
    ImageView logo_home;

    CircleImageView halamanAkun;
    ImageView postingFoto, halamanHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.feed);
        postingFoto = findViewById(R.id.postingFoto);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedList = FeedRepository.getFeedList();

        logo_home = findViewById(R.id.logo_home);
        logo_home.setOnClickListener(v -> {
            recyclerView.smoothScrollToPosition(0);
        });

        halamanAkun = findViewById(R.id.halamanAkun);
        halamanAkun.setImageResource(R.drawable.oip);
        halamanAkun.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfilePribadiActivity.class);
            feedList = FeedRepository.getFeedList();
            intent.putParcelableArrayListExtra("feedList", new ArrayList<>(feedList));
            startActivity(intent);
        });

        postingFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Pilih Foto"), 101);
        });

        adapter = new FeedAdapter(this, feedList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            ArrayList<Uri> selectedImages = new ArrayList<>();

            if (data.getClipData() != null) {
                int count = Math.min(data.getClipData().getItemCount(), 6);
                for (int i = 0; i < count; i++) {
                    selectedImages.add(data.getClipData().getItemAt(i).getUri());
                }
            } else if (data.getData() != null) {
                selectedImages.add(data.getData());
            }

            ArrayList<PhotoModel> photoModels = new ArrayList<>();
            for (Uri uri : selectedImages) {
                try {
                    Uri localUri = copyUriToInternalStorage(uri);
                    photoModels.add(new PhotoModel(localUri));
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Gagal menyimpan gambar", Toast.LENGTH_SHORT).show();
                }
            }

            Intent intent = new Intent(this, CaptionActivity.class);
            intent.putParcelableArrayListExtra("selectedPhotos", photoModels);
            startActivityForResult(intent, 102);
        } else if (requestCode == 102 && resultCode == RESULT_OK && data != null) {

            ArrayList<PhotoModel> selectedPhotos = data.getParcelableArrayListExtra("selectedPhotos");
            String caption = data.getStringExtra("caption");

            FeedModel newFeed = new FeedModel(
                    R.drawable.oip,
                    "ys_erlanggaa",
                    selectedPhotos,
                    caption,
                    0,
                    0,
                    "Aku lelah dengan semua ini",
                    "Mamamia",
                    R.drawable.oip,
                    selectedPhotos,
                    FeedRepository.getPostCount(),
                    6969,
                    1
            );

            FeedRepository.addFeed(newFeed);
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);

        }
    }

    private Uri copyUriToInternalStorage(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        if (inputStream == null) throw new FileNotFoundException("Unable to open input stream");

        File directory = new File(getFilesDir(), "images");
        if (!directory.exists()) directory.mkdirs();

        String fileName = "img_" + System.currentTimeMillis() + ".jpg";
        File outFile = new File(directory, fileName);

        try (OutputStream outputStream = new FileOutputStream(outFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }

        return Uri.fromFile(outFile);
    }

}