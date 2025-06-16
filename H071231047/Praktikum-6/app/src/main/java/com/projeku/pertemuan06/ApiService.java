package com.projeku.pertemuan06;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/character")
    Call<CharacterResponse> getCharacters(@Query("page") int page);

    @GET("api/character/{id}")
    Call<Character> getCharacterById(@Path("id") int id);
}