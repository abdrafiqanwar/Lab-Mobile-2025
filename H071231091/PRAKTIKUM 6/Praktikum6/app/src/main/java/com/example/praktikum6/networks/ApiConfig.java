package com.example.praktikum6.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    // Base URL dari API yang akan digunakan
    private static final String BASE_URL = "https://rickandmortyapi.com/api/";

    public static ApiService getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())// Mengubah JSON ke objek Java
                .build();
        return retrofit.create(ApiService.class);
    }
}