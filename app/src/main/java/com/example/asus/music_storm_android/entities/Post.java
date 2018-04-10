package com.example.asus.music_storm_android.entities;

/**
 * Created by ASUS on 2018/3/31.
 */

public class Post {

    private String msgId, msg, userName, userAvatar, time;
    private int likes, commentNum;

    public Post(String msgId, String msg, String userName, String userAvatar, int likes, int commentNum, String time) {
        this.msgId = msgId;
        this.msg = msg;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.likes = likes;
        this.commentNum = commentNum;
        this.time = time;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void addLikes() {
        this.likes++;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public void addCommentNum() {
        this.commentNum++;
    }
}
