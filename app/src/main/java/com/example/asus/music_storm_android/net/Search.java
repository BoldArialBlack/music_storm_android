package com.example.asus.music_storm_android.net;

import android.util.Log;

import com.example.asus.music_storm_android.Config;
import com.example.asus.music_storm_android.entities.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/4/3.
 */

public class Search {

    public Search(String musicName, String artistName, int page, int perpage, final SuccessCallback successCallback, final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    Log.e("SEARCH", "onSuccess: " + result);

                    switch (object.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null) {
                                List<Music> musics = new ArrayList<Music>();
                                JSONArray musicJsonArray = object.getJSONArray(Config.KEY_MUSIC);

                                JSONObject musicObj;
                                for (int i = 0; i < musicJsonArray.length(); i++) {
                                    musicObj = musicJsonArray.getJSONObject(i);
                                    musics.add(new Music(musicObj.getString(Config.KEY_MUSIC_NAME),
                                            musicObj.getString(Config.KEY_ARTIST_NAME),
                                            musicObj.getString(Config.KEY_ALBUM_NAME),
                                            musicObj.getString(Config.KEY_LENGTH),
                                            musicObj.getString(Config.KEY_THIRD_PARTY),
                                            musicObj.getString(Config.KEY_URL)
                                    ));
                                }
                                successCallback.onSuccess(object.getInt(Config.KEY_PAGE), object.getInt(Config.KEY_PERPAGE), musics);
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
                Log.e("SEARCH", "onFail");
                if (failCallback != null) {
                    failCallback.onFail(Config.RESULT_STATUS_FAIL);
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_SEARCH,
                Config.KEY_MUSIC_NAME, musicName,
                Config.KEY_ARTIST_NAME, artistName,
                Config.KEY_PAGE, page + "",
                Config.KEY_PERPAGE, perpage + ""
        );
    }

    public static interface SuccessCallback {
        void onSuccess(int page, int perpage, List<Music> musics);
    }

    public static interface FailCallback {
        void onFail(int errorCode);
    }
}
