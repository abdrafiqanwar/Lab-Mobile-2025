package com.example.praktikum_6.data.network;

import com.example.praktikum_6.data.respon.characters.CharacterResponse;
import com.example.praktikum_6.data.respon.characters.Character; // <--- TAMBAHKAN BARIS INI
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("character")
    Call<CharacterResponse> getCharacters(@Query("page") int page);

    @GET("character/{id}")
    Call<Character> getCharacterById(@Path("id") int id);
}