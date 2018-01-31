package com.example.asus.music_storm_android;

/**
 * Created by ASUS on 2018/1/31.
 */

public class LoginEvent {
    private String userName;
    private String sign;
    private boolean isLogin = false;

    public LoginEvent() {
        this.userName = "defaultUserName";
        this.sign = "this is a default user's sign. It has no meaning.";
        this.isLogin = true;
    }

    public LoginEvent(String userName, String sign) {
        this.userName = userName;
        this.sign = sign;
        this.isLogin = true;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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
                "userName='" + userName + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
