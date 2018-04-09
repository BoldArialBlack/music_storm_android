package com.example.asus.music_storm_android.net;

import android.util.Log;

import com.example.asus.music_storm_android.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUS on 2018/3/30.
 */

public class GetCode {

    public GetCode(String phone, final SuccessCallback successCallback, final FailCallback failCallback) {

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    switch (jsonObject.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null) {
                                successCallback.onSuccess();
                                Log.e("GET_CODE", "on success: " + result);
                            }
                            break;
                        default:
                            if (failCallback != null) {
                                failCallback.onFail();
                                Log.e("GET_CODE", "on fail");
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONEXCEPTION", e.toString());
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
        }, Config.KEY_ACTION, Config.ACTION_GET_CODE, Config.KEY_PHONE_NUM, phone);

    }

    public static interface SuccessCallback {
        void onSuccess();
    }

    public static interface FailCallback {
        void onFail();
    }
}
