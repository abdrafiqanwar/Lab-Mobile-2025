package com.example.praktikum_6.ui;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.praktikum_6.R;
import com.example.praktikum_6.databinding.ItemCharacterBinding;
import com.example.praktikum_6.data.respon.characters.Character; // Pastikan import ini
import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOAD_MORE = 1;

    private List<Character> characterList = new ArrayList<>();
    private boolean showLoadMore = false;
    private OnLoadMoreClickListener loadMoreClickListener;

    public interface OnLoadMoreClickListener {
        void onLoadMoreClicked();
    }

    public void setOnLoadMoreClickListener(OnLoadMoreClickListener listener) {
        this.loadMoreClickListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
        notifyDataSetChanged();
    }

    public void addCharacters(List<Character> characters) {
        int startPosition = this.characterList.size();
        this.characterList.addAll(characters);
        notifyItemRangeInserted(startPosition, characters.size());
    }

    public void setShowLoadMore(boolean show) {
        showLoadMore = show;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == characterList.size()) {
            return VIEW_TYPE_LOAD_MORE;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return characterList.size() + (showLoadMore ? 1 : 0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            ItemCharacterBinding binding = ItemCharacterBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new CharacterViewHolder(binding);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterViewHolder) {
            Character character = characterList.get(position);
            ((CharacterViewHolder) holder).bind(character);
        } else if (holder instanceof LoadMoreViewHolder) {
            ((LoadMoreViewHolder) holder).bind();
        }
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {
        private final ItemCharacterBinding binding;

        CharacterViewHolder(ItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(Character character) {
            binding.tvName.setText(character.name != null ? character.name : "Unknown");
            binding.tvStatus.setText(character.status != null ? character.status : "Unknown");
            binding.tvSpesies.setText("   -   " + (character.species != null ? character.species : "Unknown"));

            Glide.with(binding.getRoot().getContext())
                    .load(character.image)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(binding.imgCharacter);
            binding.tvName.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                // Hanya kirim ID karakter
                intent.putExtra("character_id", character.id); // Menggunakan character.id
                context.startActivity(intent);
            });
        }
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        private final Button btnLoadMore;

        LoadMoreViewHolder(View itemView) {
            super(itemView);
            btnLoadMore = itemView.findViewById(R.id.btnLoadMore);
        }

        void bind() {
            btnLoadMore.setOnClickListener(v -> {
                if (loadMoreClickListener != null) {
                    loadMoreClickListener.onLoadMoreClicked();
                }
            });
        }
    }
}