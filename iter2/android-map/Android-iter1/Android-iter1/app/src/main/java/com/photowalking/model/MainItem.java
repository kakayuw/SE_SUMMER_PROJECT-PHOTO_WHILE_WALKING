package com.photowalking.model;

/**
 * Created by lionel on 2017/7/13.
 */

public class MainItem {
    String title;
    String stime;
    String totime;
    String miles;
    String photos;

    public MainItem(String title, String stime, String totime, String miles, String photos) {
        this.title = title;
        this.stime = stime;
        this.totime = totime;
        this.miles = miles;
        this.photos = photos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getTotime() {
        return totime;
    }

    public void setTotime(String totime) {
        this.totime = totime;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

}
