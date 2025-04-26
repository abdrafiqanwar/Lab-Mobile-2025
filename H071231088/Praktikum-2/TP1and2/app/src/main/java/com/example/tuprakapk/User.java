package com.example.tuprakapk;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class User implements Parcelable {
    private String name;
    private String bio;
    private String lokasi;
    private String web;
    private String tanggal;
    private String imageProfileUri;
    private String imageHomeUri;

    // Nama untuk SharedPreferences
    private static final String PREFS_NAME = "user_prefs";

    // Kunci untuk data default di SharedPreferences
    private static final String KEY_DEFAULT_NAME = "default_name";
    private static final String KEY_DEFAULT_BIO = "default_bio";
    private static final String KEY_DEFAULT_LOKASI = "default_lokasi";
    private static final String KEY_DEFAULT_WEB = "default_web";
    private static final String KEY_DEFAULT_TANGGAL = "default_tanggal";
    private static final String KEY_DEFAULT_IMAGE_PROFILE_URI = "default_image_profile_uri";
    private static final String KEY_DEFAULT_IMAGE_HOME_URI = "default_image_home_uri";

    // URI untuk gambar profil default (pp.png)
    private static String DEFAULT_PROFILE_IMAGE_URI;

    public User(String name, String bio, String lokasi, String web, String tanggal, String imageProfileUri, String imageHomeUri) {
        this.name = name;
        this.bio = bio;
        this.lokasi = lokasi;
        this.web = web;
        this.tanggal = tanggal;
        this.imageProfileUri = imageProfileUri;
        this.imageHomeUri = imageHomeUri;
    }

    // Metode untuk membuat User dengan data default dari SharedPreferences
    public static User createDefaultUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String defaultImageProfileUri = prefs.getString(KEY_DEFAULT_IMAGE_PROFILE_URI, null);

        // Inisialisasi DEFAULT_PROFILE_IMAGE_URI jika belum diinisialisasi
        if (DEFAULT_PROFILE_IMAGE_URI == null) {
            DEFAULT_PROFILE_IMAGE_URI = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.pp).toString();
        }

        // Jika tidak ada URI di SharedPreferences, gunakan URI default
        if (defaultImageProfileUri == null) {
            defaultImageProfileUri = DEFAULT_PROFILE_IMAGE_URI;
        }

        return new User(
                prefs.getString(KEY_DEFAULT_NAME, "Nama Default"),
                prefs.getString(KEY_DEFAULT_BIO, "Bio Default"),
                prefs.getString(KEY_DEFAULT_LOKASI, ""),
                prefs.getString(KEY_DEFAULT_WEB, ""),
                prefs.getString(KEY_DEFAULT_TANGGAL, ""),
                defaultImageProfileUri,
                prefs.getString(KEY_DEFAULT_IMAGE_HOME_URI, "")
        );
    }

    // Metode untuk menyimpan data default ke SharedPreferences
    public static void saveDefaultUser(Context context, User user) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_DEFAULT_NAME, user.getName());
        editor.putString(KEY_DEFAULT_BIO, user.getBio());
        editor.putString(KEY_DEFAULT_LOKASI, user.getLokasi());
        editor.putString(KEY_DEFAULT_WEB, user.getWeb());
        editor.putString(KEY_DEFAULT_TANGGAL, user.getTanggal());
        editor.putString(KEY_DEFAULT_IMAGE_PROFILE_URI, user.getImageProfileUri());
        editor.putString(KEY_DEFAULT_IMAGE_HOME_URI, user.getImageHomeUri());
        editor.apply();
    }

    // Metode untuk mendapatkan URI gambar profil default
    public static String getDefaultProfileImageUri(Context context) {
        // Inisialisasi DEFAULT_PROFILE_IMAGE_URI jika belum diinisialisasi
        if (DEFAULT_PROFILE_IMAGE_URI == null) {
            DEFAULT_PROFILE_IMAGE_URI = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.pp).toString();
        }
        return DEFAULT_PROFILE_IMAGE_URI;
    }

    protected User(Parcel in) {
        name = in.readString();
        bio = in.readString();
        lokasi = in.readString();
        web = in.readString();
        tanggal = in.readString();
        imageProfileUri = in.readString();
        imageHomeUri = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getImageProfileUri() {
        return imageProfileUri;
    }

    public void setImageProfileUri(String imageProfileUri) {
        this.imageProfileUri = imageProfileUri;
    }

    public String getImageHomeUri() {
        return imageHomeUri;
    }

    public void setImageHomeUri(String imageHomeUri) {
        this.imageHomeUri = imageHomeUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(bio);
        dest.writeString(lokasi);
        dest.writeString(web);
        dest.writeString(tanggal);
        dest.writeString(imageProfileUri);
        dest.writeString(imageHomeUri);
    }
}