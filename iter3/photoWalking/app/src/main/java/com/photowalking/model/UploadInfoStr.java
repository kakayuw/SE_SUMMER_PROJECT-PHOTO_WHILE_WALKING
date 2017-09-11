package com.photowalking.model;

/*
 * Created by yuhan on 2017/9/10.
 */

import java.util.List;

public class UploadInfoStr {
    private String traceid;
    private int userid;
    private String username;
    private String tracename;
    private String starttime;
    private String endtime;
    private String tracedate;
    private double miles;
    private int photonumb;
    private String  polylines;
    List<UploadPhoto> photos;

    public UploadInfoStr() {}

    public UploadInfoStr(String traceid, int userid, String username, String tracename, String starttime,
                         String endtime, String tracedate, double miles, int photonumb, String polylines, List<UploadPhoto>photos) {
        this.traceid = traceid;
        this.userid = userid;
        this.username = username;
        this.tracename = tracename;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tracedate = tracedate;
        this.miles = miles;
        this.photonumb = photonumb;
        this.polylines = polylines;
        this.photos = photos;
    }

    public String getTraceid() {
        return traceid;
    }

    public void setTraceid(String traceid) {
        this.traceid = traceid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTracename() {
        return tracename;
    }

    public void setTracename(String tracename) {
        this.tracename = tracename;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTracedate() {
        return tracedate;
    }

    public void setTracedate(String tracedate) {
        this.tracedate = tracedate;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public int getPhotonumb() {
        return photonumb;
    }

    public void setPhotonumb(int photonumb) {
        this.photonumb = photonumb;
    }

    public String getPolylines() {
        return polylines;
    }

    public void setPolylines(String polylines) {
        this.polylines = polylines;
    }

    public List<UploadPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<UploadPhoto> photos) {
        this.photos = photos;
    }

}
