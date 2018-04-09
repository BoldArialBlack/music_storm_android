package com.example.asus.music_storm_android.net;

import android.util.Log;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUS on 2018/3/31.
 */

public class Login {

    public Login(final String phone_md5, String code, final SuccessCallback successCallback, final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);

                    switch (object.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null) {
                                User user = new User(object.getString(Config.KEY_USER_NAME), object.getString(Config.KEY_USER_PROFILE),
                                        object.getString(Config.KEY_USER_AVATAR), object.getInt(Config.KEY_USER_LEVEL), phone_md5);
                                successCallback.onSuccess(object.getString(Config.KEY_TOKEN), user);
                                Log.e("LOGIN", "success: " + user.toString());
                            }
                            break;
                        default:
                            if (failCallback != null) {
                                failCallback.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("LOGIN", "fail: " + e);
                    if (failCallback != null) {
                        failCallback.onFail();
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCallback != null) {
                    failCallback.onFail();
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_LOGIN, Config.KEY_PHONE_MD5, phone_md5, Config.KEY_CODE, code);
    }

    public static interface SuccessCallback {
        void onSuccess(String token, User user);
    }

    public static interface FailCallback {
        void onFail();
    }
}
