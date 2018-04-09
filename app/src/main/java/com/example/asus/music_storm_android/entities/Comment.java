package com.example.asus.music_storm_android.entities;

/**
 * Created by ASUS on 2018/4/1.
 */

public class Comment {

    private String content, userName;

    public Comment(String content, String userName) {
        this.content = content;
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
