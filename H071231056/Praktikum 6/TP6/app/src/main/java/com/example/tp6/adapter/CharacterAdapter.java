package com.example.tp6.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp6.models.Character;

import com.bumptech.glide.Glide;
import com.example.tp6.DetailActivity;
import com.example.tp6.R;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private Context context;
    private List<Character> characterList;
    private OnLoadMoreClickListener loadMoreClickListener;
    private boolean showLoadMore = false;

    public CharacterAdapter(Context context, OnLoadMoreClickListener listener) {
        this.context = context;
        this.characterList = new ArrayList<>();
        this.loadMoreClickListener = listener; // Initialize the listener
    }

    public interface OnLoadMoreClickListener {
        void onLoadMoreClick(Context context);
    }


    public void addCharacters(List<Character> newCharacters) {
        int startPosition = characterList.size();
        characterList.addAll(newCharacters);
        notifyItemRangeInserted(startPosition, newCharacters.size());
    }

    public void setShowLoadMore(boolean showLoadMore) {
        this.showLoadMore = showLoadMore;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_character, parent, false);
            return new CharacterViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterViewHolder) {
            CharacterViewHolder viewHolder = (CharacterViewHolder) holder;
            Character character = characterList.get(position);

            viewHolder.textViewName.setText(character.getName());
            viewHolder.textViewSpecies.setText("Species: " + character.getSpecies());
            viewHolder.textViewStatus.setText("Status: " + character.getStatus());

            Glide.with(context)
                    .load(character.getImage())
                    .centerCrop()
                    .into(viewHolder.imageViewCharacter);

            // Set click listener for the entire item
            viewHolder.itemView.setOnClickListener(v -> {
                // Create intent to DetailActivity
                Intent intent = new Intent(context, DetailActivity.class);

                // Pass character ID to DetailActivity
                intent.putExtra("character_id", String.valueOf(character.getId()));
                // Start DetailActivity
                context.startActivity(intent);
            });
        } else if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder viewHolder = (LoadMoreViewHolder) holder;
            viewHolder.buttonLoadMore.setOnClickListener(v -> {
                if (loadMoreClickListener != null) {
                    loadMoreClickListener.onLoadMoreClick(context);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return characterList.size() + (showLoadMore ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return position < characterList.size() ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    public void clearCharacters() {
        characterList.clear();
        notifyDataSetChanged();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCharacter;
        TextView textViewName;
        TextView textViewSpecies;
        TextView textViewStatus;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCharacter = itemView.findViewById(R.id.imageViewCharacter);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSpecies = itemView.findViewById(R.id.textViewSpecies);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
        }
    }

    public static class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        Button buttonLoadMore;

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonLoadMore = itemView.findViewById(R.id.buttonLoadMore);
        }
    }
}