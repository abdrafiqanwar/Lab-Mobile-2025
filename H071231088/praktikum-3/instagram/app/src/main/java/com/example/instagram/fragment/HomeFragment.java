package com.example.instagram.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager; // Import LayoutManager
import androidx.recyclerview.widget.RecyclerView;
import com.example.instagram.DataSource;
import com.example.instagram.PostAdapter;
import com.example.instagram.R;

public class HomeFragment extends Fragment {

    private RecyclerView rvPost;
    private PostAdapter postAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi RecyclerView dan Adapter
        rvPost = view.findViewById(R.id.rvPost);

        // Menambahkan LayoutManager agar RecyclerView dapat diatur dengan baik
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));

        postAdapter = new PostAdapter(DataSource.posts);  // DataSource.posts berisi data post
        rvPost.setAdapter(postAdapter);

        return view;
    }
}
