package com.example.tugaspraktikum3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

public class User implements Parcelable {
    private String username;
    private String profileImageUrl;
    private String bio;
    private List<Story> highlights;
    private List<Feed> feeds;
    private boolean isPersonalProfile;

    public User(String username, String profileImageUrl, String bio, List<Story> highlights, List<Feed> feeds, boolean isPersonalProfile) {
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.highlights = highlights;
        this.feeds = feeds;
        this.isPersonalProfile = isPersonalProfile;
    }

    public User(String username, String profileImageUrl, String bio, List<Story> highlights, List<Feed> feeds) {
        this(username, profileImageUrl, bio, highlights, feeds, false);
    }

    public User(String username, String profileImageUrl, List<Story> highlights, List<Feed> feeds) {
        this(username, profileImageUrl, "", highlights, feeds, false);
    }

    protected User(Parcel in) {
        username = in.readString();
        profileImageUrl = in.readString();
        bio = in.readString();
        isPersonalProfile = in.readByte() != 0;
        highlights = in.createTypedArrayList(Story.CREATOR);
        feeds = in.createTypedArrayList(Feed.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(profileImageUrl);
        dest.writeString(bio);
        dest.writeByte((byte) (isPersonalProfile ? 1 : 0));
        dest.writeTypedList(highlights);
        dest.writeTypedList(feeds);
    }

    @Override
    public int describeContents() {
        return 0;
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

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public List<Story> getHighlights() { return highlights; }
    public void setHighlights(List<Story> highlights) { this.highlights = highlights; }
    public List<Feed> getFeeds() { return feeds; }
    public void setFeeds(List<Feed> feeds) { this.feeds = feeds; }
    public boolean isPersonalProfile() { return isPersonalProfile; }
    public void setPersonalProfile(boolean personalProfile) { isPersonalProfile = personalProfile; }

    // Inner class: Feed
    public static class Feed implements Parcelable {
        private int imageResId = -1;
        private Uri imageUri = null;
        private String caption;
        private String username;

        public Feed(int imageResId, String caption, String username) {
            this.imageResId = imageResId;
            this.caption = caption;
            this.username = username;
        }

        public Feed(Uri imageUri, String caption, String username) {
            this.imageUri = imageUri;
            this.caption = caption;
            this.username = username;
        }

        protected Feed(Parcel in) {
            imageResId = in.readInt();
            String uriString = in.readString();
            if (uriString != null) {
                imageUri = Uri.parse(uriString);
            }
            caption = in.readString();
            username = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(imageResId);
            dest.writeString(imageUri != null ? imageUri.toString() : null);
            dest.writeString(caption);
            dest.writeString(username);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Feed> CREATOR = new Creator<Feed>() {
            @Override
            public Feed createFromParcel(Parcel in) {
                return new Feed(in);
            }

            @Override
            public Feed[] newArray(int size) {
                return new Feed[size];
            }
        };

        // Getter & Setter
        public int getImageResId() { return imageResId; }

        public Uri getImageUri() {
            return imageUri;
        }

        public String getCaption() { return caption; }
        public String getUsername() { return username; }
        public boolean hasImageUri() { return imageUri != null; }

        // Updated method that checks if permissions need to be taken
        public void saveImageUri(Context context) {
            if (imageUri != null) {
                try {
                    // Only try to take permission if it's a content URI
                    if ("content".equals(imageUri.getScheme())) {
                        // Check if we need to take permission (only for content:// URIs)
                        // Don't take permission if it's not needed or already granted
                        int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        context.getContentResolver().takePersistableUriPermission(
                                imageUri,
                                takeFlags
                        );
                        Log.d("User.Feed", "Successfully persisted URI permission: " + imageUri);
                    }
                } catch (SecurityException e) {
                    Log.e("User.Feed", "Error persisting URI permission: " + e.getMessage());
                    // The URI might still be accessible temporarily, so we don't clear it
                }
            }
        }
    }


    // Inner class: Story
    public static class Story implements Parcelable {
        private int imageResId; // ubah dari String ke int
        private String title;
        private String detail;

        public Story(int imageResId, String title, String detail) {
            this.imageResId = imageResId;
            this.title = title;
            this.detail = detail;
        }

        protected Story(Parcel in) {
            imageResId = in.readInt(); // baca int
            title = in.readString();
            detail = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(imageResId); // tulis int
            dest.writeString(title);
            dest.writeString(detail);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Story> CREATOR = new Creator<Story>() {
            @Override
            public Story createFromParcel(Parcel in) {
                return new Story(in);
            }

            @Override
            public Story[] newArray(int size) {
                return new Story[size];
            }
        };

        // GETTER
        public int getImageResId() { return imageResId; }
        public String getTitle() { return title; }
        public String getDetail() { return detail; }
    }

}
