package com.example.loginandregistrationapplication.models;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.List;

public class TramXe {
    @PropertyName("matramxe")
    private String matramxe;
    @PropertyName("diachi")
    private String diachi;
    @PropertyName("soluongxe")
    private int soluongxe;
    @PropertyName("latitude")
    private float latitude;
    @PropertyName("longitude")
    private float longitude;


    public TramXe() {
    }

    public TramXe(String matramxe, String diachi, int soluongxe, float latitude, float longitude) {
        this.matramxe = matramxe;
        this.diachi = diachi;
        this.soluongxe = soluongxe;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getMatramxe() {
        return matramxe;
    }

    public void setMatramxe(String matramxe) {
        this.matramxe = matramxe;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getSoluongxe() {
        return soluongxe;
    }

    public void setSoluongxe(int soluongxe) {
        this.soluongxe = soluongxe;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
