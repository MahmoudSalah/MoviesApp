package com.example.albheryyyyuhckfjhdgf.finalproject;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Trailer;

import java.util.ArrayList;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 19/09/2016.
 */
public interface OnNetworkTrailerResponseListener {
    void onSuccess(ArrayList<Trailer> asd);
    void onFailure(Exception e);
}
