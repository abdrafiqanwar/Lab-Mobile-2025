package com.example.praktikum_6.data.respon.characters;

import com.google.gson.annotations.SerializedName;

public class Character {
    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("image")
    public String image;

    @SerializedName("status")
    public String status;

    @SerializedName("species")
    public String species;

    @SerializedName("type")
    public String type;

    @SerializedName("gender")
    public String gender;

    @SerializedName("origin")
    public Origin origin;

    @SerializedName("location")
    public Location location;

    @SerializedName("episode")
    public String[] episode;

    @SerializedName("created")
    public String created;

    // Inner class Origin
    public static class Origin {
        @SerializedName("name")
        public String name;

        @SerializedName("url")
        public String url;
    }

    // Inner class Location
    public static class Location {
        @SerializedName("name")
        public String name;

        @SerializedName("url")
        public String url;
    }
}