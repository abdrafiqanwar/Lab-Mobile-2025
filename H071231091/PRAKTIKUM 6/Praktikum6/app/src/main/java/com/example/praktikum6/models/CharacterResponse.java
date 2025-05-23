package com.example.praktikum6.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CharacterResponse {

    // Field "info" akan diisi dari JSON //anotasi dari library Gson
    @SerializedName("info")
    private Info info;


    // (berisi daftar karakter)
    @SerializedName("results")
    private List<Characters> results;

    // Getter & Setter untuk info
    public Info getInfo() { return info; }
    public void setInfo(Info info) { this.info = info; }

    // Getter & Setter untuk results
    public List<Characters> getResults() { return results; }
    public void setResults(List<Characters> results) { this.results = results; }


    // Class Info merepresentasikan "info" dari response JSON
    public static class Info {
        @SerializedName("count")
        private int count; // Total karakter

        @SerializedName("pages")
        private int pages; // Total halaman

        @SerializedName("next")
        private String next;

        @SerializedName("prev")
        private String prev; // URL


        // Getter & Setter untuk semua field info
        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }

        public int getPages() { return pages; }
        public void setPages(int pages) { this.pages = pages; }

        public String getNext() { return next; }
        public void setNext(String next) { this.next = next; }

        public String getPrev() { return prev; }
        public void setPrev(String prev) { this.prev = prev; }
    }
}
