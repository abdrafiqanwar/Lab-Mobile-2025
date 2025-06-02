package com.example.tp6.api;

import com.example.tp6.models.ApiResponse;
import com.example.tp6.models.Character;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {
    @GET("character")
    Call<ApiResponse> getCharacters(@Query("page") int page);

    @GET("character/{id}")
    Call<Character> getCharacterById(@Path("id") String id);
}