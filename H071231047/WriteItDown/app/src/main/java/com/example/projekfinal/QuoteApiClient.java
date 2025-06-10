package com.example.projekfinal;

import android.os.Handler;

import androidx.annotation.NonNull;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// In QuoteApiClient.java - Add retry capability and better error detection
public class QuoteApiClient {
    private final ZenQuotesApiService apiService;
    private static final int MAX_RETRIES = 2;
    private static final int RETRY_DELAY_MS = 3000; // 3 seconds delay between retries

    public interface QuoteCallback {
        void onSuccess(Quote quote);
        void onError(String errorMessage);
    }

    public QuoteApiClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        apiService = new Retrofit.Builder()
                .baseUrl("https://zenquotes.io/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ZenQuotesApiService.class);
    }

    public void getRandomQuote(final QuoteCallback callback) {
        getRandomQuoteWithRetry(callback, 0);
    }

    private void getRandomQuoteWithRetry(final QuoteCallback callback, final int retryCount) {
        apiService.getRandomQuote().enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(@NonNull Call<List<Quote>> call, @NonNull Response<List<Quote>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    callback.onSuccess(response.body().get(0));
                } else {
                    // Check if we should retry
                    if (retryCount < MAX_RETRIES) {
                        retryAfterDelay(callback, retryCount);
                    } else {
                        int code = response.code();
                        if (code == 429) { // Too Many Requests
                            callback.onError("Rate limited by API. Please try again in a minute.");
                        } else {
                            callback.onError("Error loading quote (HTTP " + code + ")");
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Quote>> call, @NonNull Throwable t) {
                if (retryCount < MAX_RETRIES) {
                    retryAfterDelay(callback, retryCount);
                } else {
                    callback.onError("Connection error: " + t.getMessage());
                }
            }
        });
    }

    private void retryAfterDelay(final QuoteCallback callback, final int retryCount) {
        new Handler().postDelayed(() ->
                        getRandomQuoteWithRetry(callback, retryCount + 1),
                RETRY_DELAY_MS);
    }
}