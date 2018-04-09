package com.example.asus.music_storm_android;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.asus.music_storm_android.entities.User;

/**
 * Created by ASUS on 2018/3/30.
 */

public class Config {

    public static final String SERVER_URL = "http://10.242.52.238:8080/TomcatTest/musicstorm_api.jsp";
//    public static final String SERVER_URL = "http://demo.eoeschool.com/api/v1/nimings/io";

    public static final String KEY_TOKEN = "token";
    public static final String KEY_ACTION = "action";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CODE = "code ";
    public static final String KEY_USER = "user";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_PROFILE = "userProfile";
    public static final String KEY_USER_AVATAR = "userAvatar";
    public static final String KEY_PAGE = "page";
    public static final String KEY_PERPAGE = "perpage";
    public static final String KEY_MUSIC = "music";
    public static final String KEY_MUSIC_NAME = "musicName";
    public static final String KEY_ARTIST_NAME = "artistName";
    public static final String KEY_ALBUM_NAME = "albumName";
    public static final String KEY_LENGTH = "length";
    public static final String KEY_THIRD_PARTY = "thirdParty";
    public static final String KEY_URL = "url";
    public static final String KEY_POST = "post";
    public static final String KEY_MSG_ID = "msgId";
    public static final String KEY_MSG = "msg";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_COMMENT_NUM = "commentNum";
    public static final String KEY_TIME = "time";
    public static final String KEY_COMMENTS = "comments";
    public static final String KEY_CONTENT = "content";


    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;

    public static final String APP_ID = "com.example.asus.music_storm_android";
    public static final String CHARSET = "utf-8";


    public static final String ACTION_GET_CODE = "send_pass";
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_UPLOAD_USER = "upload_user";
    public static final String ACTION_SEARCH = "search";
    public static final String ACTION_GET_POST = "get_post";
    public static final String ACTION_PUBLISH = "publish";
    public static final String ACTION_GET_COMMENT = "get_comment";
    public static final String ACTION_PUB_COMMENT = "pub_comment";

    public static final int ACTIVITY_RESULT_NEED_REFRESH = 10000;

    public static String getCachedToken(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    public static void cacheToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public static User getCachedUser(Context context) {
        String userName = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_USER_NAME, null);
        String userProfile = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_USER_PROFILE, null);
        String userAvatar = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_USER_AVATAR, null);
        return new User(userName, userProfile, userName);
    }

    public static void cacheUser(Context context, User user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_USER_NAME, user.getUserName());
        editor.putString(KEY_USER_PROFILE, user.getUserProfile());
        editor.putString(KEY_USER_AVATAR, user.getUserAvatar());
        editor.apply();
    }

    public static String getCachedPhoneNum(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE_NUM, null);
    }

    public static void cachePhoneNum(Context context, String phoneNum) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PHONE_NUM, phoneNum);
        editor.apply();
    }
}
