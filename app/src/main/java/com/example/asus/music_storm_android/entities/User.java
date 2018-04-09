package com.example.asus.music_storm_android.entities;

import java.io.Serializable;

/**
 * Created by ASUS on 2018/4/3.
 */

public class User implements Serializable {
    private String userName, userProfile, userAvatar;
    private int userLevel;
    private String phone_md5;

    public User(String userName, String userProfile, String userAvatar, int userLevel, String phone_md5) {
        this.userName = userName;
        this.userProfile = userProfile;
        this.userAvatar = userAvatar;
        this.userLevel = userLevel;
        this.phone_md5 = phone_md5;
    }

/*    public User(String phone) {
        this.userName = phone;
        this.userProfile = "this is a default user's sign. It has no meaning.";
        this.phone = phone;
        userLevel = 0;
    }

    public User() {
        userName = "defaultUserName";
        userProfile = "this is a default user's sign. It has no meaning.";
        userLevel = 0;
    }*/

    public String getPhone() {
        return phone_md5;
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

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userProfile='" + userProfile + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userLevel=" + userLevel +
                ", phone='" + phone_md5 + '\'' +
                '}';
    }
}
