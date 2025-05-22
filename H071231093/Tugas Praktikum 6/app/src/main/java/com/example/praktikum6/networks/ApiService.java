package com.example.praktikum6.networks;

import com.example.praktikum6.models.CharacterResponse;
import com.example.praktikum6.models.Characters;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("character")
    Call<CharacterResponse> getCharacters(@Query("page") int page);

    @GET("character/{id}")
    Call<Characters> getCharacterById(@Path("id") int id);
}