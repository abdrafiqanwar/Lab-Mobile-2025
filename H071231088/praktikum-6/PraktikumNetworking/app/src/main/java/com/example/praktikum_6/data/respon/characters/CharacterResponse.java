package com.example.praktikum_6.data.respon.characters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterResponse {
    @SerializedName("info")
    private Info info;

    @SerializedName("results")
    private List<com.example.praktikum_6.data.respon.characters.Character> results;

    public Info getInfo() {
        return info;
    }

    public List<Character> getResults() {
        return results;
    }

    public static class Info {
        @SerializedName("next")
        private String next;

        public String getNext() {
            return next;
        }
    }
}