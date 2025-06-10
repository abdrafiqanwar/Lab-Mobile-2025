package com.example.projekfinal;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ZenQuotesApiService {
    @GET("api/random")
    Call<List<Quote>> getRandomQuote();
}