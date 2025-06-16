package com.projeku.pertemuan06;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<Character> characterList;

    public CharacterAdapter(List<Character> characterList) {
        this.characterList = characterList;
    }

    public void addCharacters(List<Character> newCharacters) {
        int startPosition = characterList.size();
        characterList.addAll(newCharacters);
        notifyItemRangeInserted(startPosition, newCharacters.size());
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characterList.get(position);
        holder.bind(character);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatarImageView;
        private TextView nameTextView;
        private TextView speciesTextView;
        private TextView statusView;
        private Character character;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            speciesTextView = itemView.findViewById(R.id.speciesTextView);
            statusView = itemView.findViewById(R.id.statusView);

            avatarImageView.setOnClickListener(v -> {
                if (character != null) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    // Pass only the character ID instead of all details
                    intent.putExtra("id", character.getId());
                    context.startActivity(intent);
                }
            });
        }

        public void bind(Character character) {
            this.character = character;
            Picasso.get().load(character.getImage()).into(avatarImageView);
            nameTextView.setText(character.getName());
            speciesTextView.setText(character.getSpecies());

            // Set the status text
            String status = character.getStatus();
            statusView.setText(status != null ? status : "Unknown");

            // Set appropriate background color based on status
            int backgroundTintResId;
            if (status != null) {
                switch (status.toLowerCase()) {
                    case "alive":
                        backgroundTintResId = R.color.status_alive;
                        break;
                    case "dead":
                        backgroundTintResId = R.color.status_dead;
                        break;
                    default:
                        backgroundTintResId = R.color.status_unknown;
                        break;
                }
            } else {
                backgroundTintResId = R.color.status_unknown;
            }

            statusView.setBackgroundTintList(
                    ContextCompat.getColorStateList(itemView.getContext(), backgroundTintResId)
            );
        }
    }
}