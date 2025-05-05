package com.example.instagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.adapter.SelectedPhotoAdapter;
import com.example.instagram.model.PhotoModel;

import java.util.ArrayList;

public class CaptionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText captionInput;
    private Button postButton;

    private ArrayList<PhotoModel> selectedPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption);

        recyclerView = findViewById(R.id.recyclerViewSelectedPhotos);
        captionInput = findViewById(R.id.captionInput);
        postButton = findViewById(R.id.postButton);

        selectedPhotos = getIntent().getParcelableArrayListExtra("selectedPhotos");
        if (selectedPhotos == null || selectedPhotos.isEmpty()) {
            Toast.makeText(this, "No photos selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new SelectedPhotoAdapter(this, selectedPhotos));

        postButton.setOnClickListener(v -> {
            String caption = captionInput.getText().toString().trim();

            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("selectedPhotos", selectedPhotos);
            resultIntent.putExtra("caption", caption);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
