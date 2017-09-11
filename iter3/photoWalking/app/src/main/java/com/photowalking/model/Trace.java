package com.photowalking.model;

/*
 * Created by yuhang on 2017/7/20.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Trace")
public class Trace extends Model {
    // If name is omitted, then the field name is used.
    @Column(name = "traceid", index = true)
    private String traceid;

    @Column(name = "userid", index = true)
    private int userid;

    @Column(name = "username")
    private String username;

    @Column(name = "tracename")
    private String tracename;

    @Column(name = "starttime")
    private String starttime;

    @Column(name = "endtime")
    private String endtime;



    @Column(name = "tracedate")
    private String tracedate;

    @Column(name = "miles")
    private double miles;

    @Column(name = "photonumb")
    private int photonumb;

    @Column(name = "polylines")
    private String  polylines;

    public List<Photo> items() {
        return getMany(Photo.class, "traceid");
    }

    public Trace() {
        super();
    }

    public Trace(String traceid, int userid, String username,String tracename, String starttime, String endtime, String tracedate, double miles, int photonumb, String polylines) {
        super();
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

    public String getTracename() {
        return tracename;
    }

    public void setTracename(String tracename) {
        this.tracename = tracename;
    }
}