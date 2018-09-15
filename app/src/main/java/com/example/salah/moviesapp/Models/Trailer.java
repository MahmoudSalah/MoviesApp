package com.example.albheryyyyuhckfjhdgf.finalproject.Models;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 17/09/2016.
 */
public class Trailer {
    String name;
    String key;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trailer(){

    }
    public Trailer(String key, String name) {
        this.key = key;
        this.name = name;
    }
}
