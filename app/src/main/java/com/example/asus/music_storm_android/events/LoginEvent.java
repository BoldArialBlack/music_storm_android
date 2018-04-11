package com.example.asus.music_storm_android.events;

import com.example.asus.music_storm_android.entities.User;

/**
 * Created by ASUS on 2018/1/31.
 */

public class LoginEvent {
    /*    private String userName;
        private String sign;
        private String phone;*/
    private String token;
    private User user;
    private boolean isLogin = false;

    public LoginEvent(String token, User u) {
        this.token = token;
        user = new User(u.getUserName(), u.getUserProfile(), u.getUserAvatar(), u.getUserLevel(), u.getPhone());
        this.isLogin = true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    @Override
    public String toString() {
        return "LoginEvent{" +
                "user=" + user +
                ", isLogin=" + isLogin +
                '}';
    }
}
