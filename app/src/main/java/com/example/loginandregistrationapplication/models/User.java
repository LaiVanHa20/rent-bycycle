package com.example.loginandregistrationapplication.models;

import java.io.Serializable;

public class User implements Serializable {
    private String uid;
    private String hoten;
    private String email, sodt;
    private String matkhau;
    private float sodu;

    public User(String uid, String hoten, String email, String sodt, String matkhau, float sodu) {
        this.uid = uid;
        this.hoten = hoten;
        this.email = email;
        this.sodt = sodt;
        this.matkhau = matkhau;
        this.sodu = sodu;
    }

    public float getSodu() {
        return sodu;
    }

    public void setSodu(float sodu) {
        this.sodu = sodu;
    }

    public User() {
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSodt() {
        return sodt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public User(String uid, String hoten, String email, String sodt, String matkhau) {
        this.uid = uid;
        this.hoten = hoten;
        this.email = email;
        this.sodt = sodt;
        this.matkhau = matkhau;
    }
}
