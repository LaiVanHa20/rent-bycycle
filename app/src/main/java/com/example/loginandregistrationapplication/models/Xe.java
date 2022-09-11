package com.example.loginandregistrationapplication.models;

import java.io.Serializable;

public class Xe implements Serializable {
    private String id; // tu tao
    private String maxe;

    public Xe(String maxe) {
        this.maxe = maxe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Xe() {
    }

    public String getMaxe() {
        return maxe;
    }

    public void setMaxe(String maxe) {
        this.maxe = maxe;
    }

    public Xe(String id, String maxe) {
        this.id = id;
        this.maxe = maxe;
    }
}
