package com.photowalking.model;

/**
 * Created by liujinxu on 17/6/28.
 */

public class PhotoInfo {
    String traceId;
    String photoId;
    Double lat;
    Double lon;
    String infoFileName;

    public String getInfoFileName() {
        return infoFileName;
    }

    public void setInfoFileName(String infoFileName) {
        this.infoFileName = infoFileName;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public PhotoInfo() {
    }
}
