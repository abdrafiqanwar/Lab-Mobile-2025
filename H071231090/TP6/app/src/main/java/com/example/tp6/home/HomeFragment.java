package com.example.tp6.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tp6.R;
import com.example.tp6.data.network.ApiConfig;
import com.example.tp6.data.network.ApiService;
import com.example.tp6.data.network.NetworkUtil;
import com.example.tp6.data.response.User;
import com.example.tp6.data.response.UserResponse;
import com.example.tp6.databinding.FragmentHomeBinding;
import com.example.tp6.ui.UserAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private UserAdapter userAdapter;
    private int currentPage = 1;
    private boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.userRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        userAdapter = new UserAdapter(new ArrayList<>());
        binding.userRecycler.setAdapter(userAdapter);
        userAdapter.setOnUserClickListener(user -> {
            int characterId = user.getId();
            DetailFragment detailFragment = DetailFragment.newInstance(characterId);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        binding.refresh.setOnClickListener(v -> {
            loadUsers(currentPage);
        });

        loadUsers(currentPage);

        userAdapter.setOnLoadMoreClickListener(() -> {
            if (!isLoading) {
                currentPage++;
                loadUsers(currentPage);
            }
        });

        return binding.getRoot();
    }

    private void loadUsers(int page) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showError(true, "Gagal memuat data");
            return;
        }

        showError(false, null);
        isLoading = true;
        binding.pb.setVisibility(View.VISIBLE);

        ApiService apiService = ApiConfig.getClient().create(ApiService.class);
        apiService.getCharacterData(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                isLoading = false;
                binding.pb.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<User> newUsers = response.body().getResults();
                    if (page == 1) userAdapter.clearUsers(); // optional: reset data
                    userAdapter.addMoreUsers(newUsers);
                    userAdapter.setShowLoadMore(!newUsers.isEmpty());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                isLoading = false;
                binding.pb.setVisibility(View.GONE);
                showError(true, "Gagal memuat data.\n" + t.getMessage());
            }
        });
    }

    private void showError(boolean show, String message) {
        binding.errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.userRecycler.setVisibility(show ? View.GONE : View.VISIBLE);
        binding.pesan.setText(message != null ? message : "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}