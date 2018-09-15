package com.example.albheryyyyuhckfjhdgf.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Movie movie = (Movie) getIntent().getSerializableExtra("name");
        String function = getIntent().getStringExtra("funct");
        Bundle bundle=new Bundle();
        bundle.putSerializable("name",movie);
        bundle.putString("funct",function);
        DetailFragment detailFragment = new DetailFragment(this);
        detailFragment.setArguments(bundle);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container1, detailFragment)
                    .commit();

        }


    }

}
