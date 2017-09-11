package com.photowalking.model;

/**
 * Created by lionel on 2017/8/17.
 */

public class ApplyInfo {
    private int uida;
    private int uidb;
    private String unamea;
    private String remarka;
    private String info;


    public ApplyInfo() {
    }

    public ApplyInfo(int uida, int uidb, String unamea,
                     String remarka, String info) {
        this.uida = uida;
        this.uidb = uidb;
        this.unamea = unamea;
        this.remarka = remarka;
        this.info = info;
    }

    public int getUida() {
        return uida;
    }

    public void setUida(int uida) {
        this.uida = uida;
    }

    public int getUidb() {
        return uidb;
    }

    public void setUidb(int uidb) {
        this.uidb = uidb;
    }

    public String getUnamea() {
        return unamea;
    }

    public void setUnamea(String unamea) {
        this.unamea = unamea;
    }

    public String getRemarka() {
        return remarka;
    }

    public void setRemarka(String remarka) {
        this.remarka = remarka;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
