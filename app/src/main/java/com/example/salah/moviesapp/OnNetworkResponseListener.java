package com.example.albheryyyyuhckfjhdgf.finalproject;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Movie;

import java.util.ArrayList;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 13/08/2016.
 */
public interface OnNetworkResponseListener {
    void onSuccess(ArrayList<Movie> asd);
    void onFailure(Exception e);
}
