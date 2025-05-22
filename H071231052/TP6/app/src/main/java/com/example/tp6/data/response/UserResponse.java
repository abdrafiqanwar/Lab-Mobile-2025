package com.example.tp6.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("results")
    private List<User> results;

    public List<User> getResults() {
        return results;
    }
}