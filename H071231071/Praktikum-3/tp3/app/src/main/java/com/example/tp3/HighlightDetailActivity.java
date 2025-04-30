package com.example.tp3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.Adapter.DetailHighlightAdapter;
import com.example.tp3.Model.Highlight;

import java.util.ArrayList;
import java.util.List;

public class HighlightDetailActivity extends AppCompatActivity {

    private RecyclerView rvHighlightsDetail;
    private DetailHighlightAdapter detailHighlightAdapter;
    private List<Highlight> highlightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlight_detail);

        // Initialize RecyclerView
        rvHighlightsDetail = findViewById(R.id.rv_highlights_detail);

        // Get data from intent
        String title = getIntent().getStringExtra("HIGHLIGHT_TITLE");
        int coverImage = getIntent().getIntExtra("HIGHLIGHT_IMAGE", 0);
        int detailStory = getIntent().getIntExtra("HIGHLIGHT_DETAIL_STORY", -1);

        // Create list and add the highlight
        highlightList = new ArrayList<>();
        highlightList.add(new Highlight(title, coverImage, detailStory));

        // Set up RecyclerView
        detailHighlightAdapter = new DetailHighlightAdapter(this, highlightList);
        rvHighlightsDetail.setLayoutManager(new LinearLayoutManager(this));
        rvHighlightsDetail.setAdapter(detailHighlightAdapter);


    }


}