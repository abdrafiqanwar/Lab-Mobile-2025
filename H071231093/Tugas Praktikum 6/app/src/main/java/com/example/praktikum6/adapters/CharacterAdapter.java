package com.example.praktikum6.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.praktikum6.DetailActivity;
import com.example.praktikum6.R;
import com.example.praktikum6.models.Characters;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private Context context;
    private List<Characters> charactersList = new ArrayList<>();

    public CharacterAdapter(Context context) {
        this.context = context;
    }

    public void setCharactersList(List<Characters> charactersList) {
        this.charactersList.clear();
        this.charactersList.addAll(charactersList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Characters character = charactersList.get(position);

        holder.tvName.setText(character.getName());
        holder.tvSpecies.setText("Species: " + character.getSpecies());
        holder.tvStatus.setText("Status: " + character.getStatus());

        Glide.with(context)
                .load(character.getImage())
                .into(holder.ivCharacter);

        holder.ivCharacter.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("CHARACTER", character);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return charactersList.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCharacter;
        TextView tvName, tvSpecies, tvStatus;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCharacter = itemView.findViewById(R.id.iv_character);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSpecies = itemView.findViewById(R.id.tv_species);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}