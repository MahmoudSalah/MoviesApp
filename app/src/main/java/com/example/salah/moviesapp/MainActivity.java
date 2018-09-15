package com.example.albheryyyyuhckfjhdgf.finalproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Movie;

public class MainActivity extends AppCompatActivity implements MovieListener {

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private Boolean mTablet;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FrameLayout container2 = (FrameLayout) findViewById(R.id.container2);
        if(container2 == null){
            mTablet = false;
        }else {
            mTablet = true;
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieFragment(this))
                    .commit();
        }
    }

    @Override
    public void setSelectedMovie(Movie movie,String function) {
        if(mTablet){
            DetailFragment detailFragment = new DetailFragment(this);
            Bundle bundle = new Bundle();
            bundle.putSerializable("name",movie);
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container2, detailFragment)
                    .commit();

        }else {
            startActivity(new Intent(this, DetailActivity.class).putExtra("name", movie).putExtra("funct",function));
        }
    }

    private boolean isTablet() {
        return (this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
