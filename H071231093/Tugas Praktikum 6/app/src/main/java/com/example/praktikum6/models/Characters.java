package com.example.praktikum6.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Characters implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("species")
    private String species;

    @SerializedName("type")
    private String type;

    @SerializedName("gender")
    private String gender;

    @SerializedName("image")
    private String image;

    @SerializedName("url")
    private String url;

    @SerializedName("created")
    private String created;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getCreated() { return created; }
    public void setCreated(String created) { this.created = created; }
}