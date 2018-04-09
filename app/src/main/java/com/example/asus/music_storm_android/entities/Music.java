package com.example.asus.music_storm_android.entities;

/**
 * Created by ASUS on 2018/4/3.
 */

public class Music {

    private String musicName, artistName, albumName, length, thirdParty, url;

    public Music(String musicName, String artistName, String albumName, String length, String thirdParty, String url) {
        this.musicName = musicName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.length = length;
        this.thirdParty = thirdParty;
        this.url = url;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
