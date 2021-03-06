package com.example.asus.music_storm_android.net;

import android.util.Log;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.entities.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/3/31.
 */

public class Timeline {

    public Timeline(String phone_md5, String token, int page, int perpage, final SuccessCallback successCallback, final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);

                    switch (object.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null) {
                                List<Post> msgs = new ArrayList<Post>();
                                JSONArray msgJsonArray = object.getJSONArray(Config.KEY_POST);

                                JSONObject msgObj;
                                for (int i = 0; i < msgJsonArray.length(); i++) {
                                    msgObj = msgJsonArray.getJSONObject(i);
                                    msgs.add(new Post(msgObj.getString(Config.KEY_MSG_ID),
                                            msgObj.getString(Config.KEY_MSG),
                                            msgObj.getString(Config.KEY_USER_NAME),
                                            msgObj.getString(Config.KEY_USER_AVATAR),
                                            msgObj.getInt(Config.KEY_LIKES),
                                            msgObj.getInt(Config.KEY_COMMENT_NUM),
                                            msgObj.getString(Config.KEY_TIME)
                                    ));
                                }

                                successCallback.onSuccess(object.getInt(Config.KEY_PAGE), object.getInt(Config.KEY_PERPAGE), msgs);
                            }
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            if (failCallback != null) {
                                failCallback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;
                        default:
                            if (failCallback != null) {
                                failCallback.onFail(Config.RESULT_STATUS_FAIL);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback != null) {
                        Log.e("TIMELINE", "JSONException: " + e);
                        failCallback.onFail(Config.RESULT_STATUS_FAIL);
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCallback != null) {
                    failCallback.onFail(Config.RESULT_STATUS_FAIL);
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_GET_POST,
                Config.KEY_PHONE_MD5, phone_md5,
                Config.KEY_TOKEN, token,
                Config.KEY_PAGE, page + "",
                Config.KEY_PERPAGE, perpage + "");
    }

    public static interface SuccessCallback {
        void onSuccess(int page, int perpage, List<Post> timeline);
    }

    public static interface FailCallback {
        void onFail(int errorCode);
    }
}
