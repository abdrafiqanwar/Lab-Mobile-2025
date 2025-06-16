package com.example.tp6.data.network;

import com.example.tp6.data.response.User;
import com.example.tp6.data.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("character")
    Call<UserResponse> getCharacterData(@Query("page") int page);

    @GET("character/{id}")
    Call<User> getCharacterById(@Path("id") int id);
}