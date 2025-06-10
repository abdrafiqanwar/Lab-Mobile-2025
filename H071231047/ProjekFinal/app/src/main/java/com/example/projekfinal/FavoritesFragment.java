package com.example.projekfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements NoteAdapter.OnItemClickListener, NoteAdapter.OnFavoriteToggleListener {

    private RecyclerView recyclerViewFavorites;
    private TextView textViewNoFavorites;
    private DatabaseHelper dbHelper;
    private List<Note> favoriteNotes;
    private NoteAdapter favoriteAdapter;

    private ActivityResultLauncher<Intent> editNoteLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerViewFavorites = view.findViewById(R.id.recyclerViewFavorites);
        textViewNoFavorites = view.findViewById(R.id.textViewNoFavorites);

        dbHelper = new DatabaseHelper(requireContext());
        favoriteNotes = new ArrayList<>();

        setupRecyclerView();
        setupActivityLauncher();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteNotes();
    }

    private void setupRecyclerView() {
        favoriteAdapter = new NoteAdapter(requireContext(), favoriteNotes, this);
        favoriteAdapter.setOnFavoriteToggleListener(this);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewFavorites.setAdapter(favoriteAdapter);
    }

    private void setupActivityLauncher() {
        editNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> loadFavoriteNotes()
        );
    }

    private void loadFavoriteNotes() {
        favoriteNotes.clear();
        favoriteNotes.addAll(dbHelper.getFavoriteNotes());
        favoriteAdapter.notifyDataSetChanged();

        // Show/hide no favorites message
        if (favoriteNotes.isEmpty()) {
            recyclerViewFavorites.setVisibility(View.GONE);
            textViewNoFavorites.setVisibility(View.VISIBLE);
        } else {
            recyclerViewFavorites.setVisibility(View.VISIBLE);
            textViewNoFavorites.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(Note note) {
        Intent intent = new Intent(requireContext(), AddEditNoteActivity.class);
        intent.putExtra("note_id", note.getId());
        editNoteLauncher.launch(intent);
    }

    @Override
    public void onFavoriteToggled(long noteId) {
        // Refresh the list when a note is un-favorited
        loadFavoriteNotes();
    }
}