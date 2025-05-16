package com.example.tp6;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("character")
    Call<UserResponse> getCharacters(@Query("page") int page);

    // Menambahkan endpoint untuk mendapatkan detail user berdasarkan ID dengan path parameter
    @GET("character/{id}")
    Call<User> getUserDetail(@Path("id") int id);  // Gunakan @Path untuk parameter di path URL
}