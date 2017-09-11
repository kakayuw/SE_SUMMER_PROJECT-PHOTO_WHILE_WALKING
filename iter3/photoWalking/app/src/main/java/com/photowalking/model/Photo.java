package com.photowalking.model;

/*
 * Created by yuhan on 2017/7/20.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Photos")
public class Photo extends Model {
    // If name is omitted, then the field name is used.
    @Column(name = "photoid", index = true)
    public String photoid;

    @Column(name = "traceid")
    public String traceid;

    @Column(name = "lat")
    public double lat;

    @Column(name = "lon")
    public double lon;

    @Column(name = "time")
    public String time;

    @Column(name = "filename")
    public String filename;

    @Column(name = "path")
    public String path;

    @Column(name = "geo")
    public String geo;

    public Photo() {
        super();
    }

    public Photo(String photoid, String traceid, double lat, double lon, String time, String filename, String path, String geo) {
        super();
        this.photoid = photoid;
        this.traceid = traceid;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.filename = filename;
        this.path = path;
        this.geo = geo;
    }

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

    public String getTraceid() {
        return traceid;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public void setTraceid(String traceid) {
        this.traceid = traceid;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
