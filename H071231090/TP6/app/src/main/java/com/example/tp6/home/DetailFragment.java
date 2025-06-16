package com.example.tp6.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.tp6.data.network.ApiConfig;
import com.example.tp6.data.network.ApiService;
import com.example.tp6.data.response.User;
import com.example.tp6.databinding.FragmentDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;

    public static DetailFragment newInstance(int characterId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("characterId", characterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        binding.back.setOnClickListener(v -> requireActivity().onBackPressed());

        int characterId = getArguments().getInt("characterId");
        showLoadingState();
        getCharacterById(characterId);

        binding.refresh.setOnClickListener(v -> {
            showLoadingState();
            getCharacterById(characterId);
        });

        return binding.getRoot();
    }

    private void getCharacterById(int characterId) {
        ApiService apiService = ApiConfig.getClient().create(ApiService.class);
        apiService.getCharacterById(characterId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    displayCharacterDetails(user);
                    showContentState();
                } else {
                    showErrorState();
                    Toast.makeText(requireContext(), "Gagal memuat data karakter", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showErrorState();
                Toast.makeText(requireContext(), "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoadingState() {
        binding.pb.setVisibility(View.VISIBLE);
        binding.data.setVisibility(View.GONE);
        binding.errorLayout.setVisibility(View.GONE);
    }

    private void showContentState() {
        binding.pb.setVisibility(View.GONE);
        binding.data.setVisibility(View.VISIBLE);
        binding.errorLayout.setVisibility(View.GONE);
    }

    private void showErrorState() {
        binding.pb.setVisibility(View.GONE);
        binding.data.setVisibility(View.GONE);
        binding.errorLayout.setVisibility(View.VISIBLE);
    }

    private void displayCharacterDetails(User user) {
        if (user != null) {
            binding.name.setText(user.getName());
            binding.species.setText(user.getSpecies());
            binding.status.setText(user.getStatus());
            binding.gender.setText(user.getGender());
            Glide.with(requireContext())
                    .load(user.getImage())
                    .into(binding.image);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}