package com.photowalking.model;

public class RowModel {
    private String traceName;
    private String beginTime;
    private String endTime;
    private String photoNumb;
    private String mile;
    private String date;
    private String traceId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTraceName() {
        return traceName;
    }

    public void setTraceName(String traceName) {
        this.traceName = traceName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPhotoNumb() {
        return photoNumb;
    }

    public void setPhotoNumb(String photoNumb) {
        this.photoNumb = photoNumb;
    }

    public String getMile() {
        return mile;
    }

    public void setMile(String mile) {
        this.mile = mile;
    }

    public RowModel() {
    }

}
