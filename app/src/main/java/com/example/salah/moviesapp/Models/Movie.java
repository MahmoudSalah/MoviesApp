package com.example.albheryyyyuhckfjhdgf.finalproject.Models;

import java.io.Serializable;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 23/07/2016.
 */
public class Movie implements Serializable {

    int id;
    String title;
    String releaseDate;
    String poster_path;
    String vote_average;
    String overview;

    public Movie(int id, String title,String releaseDate,String poster_path, String vote_average , String overview) {
        this.id=id;
        this.poster_path = poster_path;
        this.releaseDate = releaseDate;
        this.title = title;
        this.vote_average = vote_average;
        this.overview=overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }


    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }
}
