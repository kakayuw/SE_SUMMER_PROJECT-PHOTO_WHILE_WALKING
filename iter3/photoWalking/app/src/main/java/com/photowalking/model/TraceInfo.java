package com.photowalking.model;

/**
 * Created by liujinxu on 17/6/28.
 */

public class TraceInfo {
    private String traceId;
    private String traceName;
    private String fileName;
    private String infoFileName;
    private String traceDate;
    private String startTime;
    private String endTime;
    private String userid;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceName() {
        return traceName;
    }

    public void setTraceName(String traceName) {
        this.traceName = traceName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getInfoFileName() {
        return infoFileName;
    }

    public void setInfoFileName(String infoFileName) {
        this.infoFileName = infoFileName;
    }
    public String getTraceDate() {
        return traceDate;
    }

    public void setTraceDate(String traceDate) {
        this.traceDate = traceDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
