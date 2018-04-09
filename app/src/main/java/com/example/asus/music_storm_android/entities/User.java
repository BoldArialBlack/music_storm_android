package com.example.asus.music_storm_android.entities;

/**
 * Created by ASUS on 2018/4/3.
 */

public class User {
    private String userName, userProfile, userAvatar;

    public User(String userName, String userProfile, String userAvatar) {
        this.userName = userName;
        this.userProfile = userProfile;
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
