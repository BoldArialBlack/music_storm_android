package com.example.asus.music_storm_android.net;

import com.example.asus.music_storm_android.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUS on 2018/4/2.
 */

public class PubComment {
    public PubComment(String phone_md5, String token, String content, String msgId, final SuccessCallback successCallback, final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    switch (object.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null) {
                                successCallback.onSuccess();
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
        }, Config.KEY_ACTION, Config.ACTION_PUB_COMMENT,
                Config.KEY_TOKEN, token,
                Config.KEY_PHONE_MD5, phone_md5,
                Config.KEY_CONTENT, content,
                Config.KEY_MSG_ID, msgId);
    }

    public static interface SuccessCallback {
        void onSuccess();
    }

    public static interface FailCallback {
        void onFail(int errorCode);
    }
}
