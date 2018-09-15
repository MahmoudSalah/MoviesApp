package com.example.albheryyyyuhckfjhdgf.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.albheryyyyuhckfjhdgf.finalproject.Adapters.MoviesAdapter;
import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Movie;

import java.util.ArrayList;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 17/09/2016.
 */

public class MovieFragment extends Fragment{
    Context context;
    MoviesAdapter moviesAdapter;
    String function="popular" ;
     RecyclerView recyclerView ;
    ArrayList<Movie>  moviesList;
    Movie movie;
    Movie movie1;
    Boolean favorited=false;
    static int moviesSize;
    int id;
    String title;
    String releaseDate;
    String poster_path;
    String vote_average;
    String overview;

    MoviesDatabaseHelper moviesDatabaseHelper;

    public MovieFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public MovieFragment(Context context) {
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.content_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_Movies);

        FrameLayout container2 = (FrameLayout) v.findViewById(R.id.container2);
        if(container2 == null) {
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        if (isOnline()) {
            updateMovie();
        } else {
            moviesDatabaseHelper = new MoviesDatabaseHelper(context,function);

                Cursor result = moviesDatabaseHelper.getMovies();
                if (result.getCount() == 0) {
                    Toast.makeText(context, "Database is empty", Toast.LENGTH_SHORT).show();
                    return null;
                }
                moviesList = new ArrayList<Movie>();
                int num = moviesSize;
                while (num >= 0) {
                    result.moveToNext();
                    movie = new Movie(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6));
                    moviesList.add(movie);
                    num--;
                    moviesAdapter = new MoviesAdapter(moviesList, context, function);
                    recyclerView.setAdapter(moviesAdapter);
                }
            }

        return v;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

//    public boolean isOnline() {
//        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }


    void updateMovie() {
        if(favorited == false) {
            moviesDatabaseHelper = new MoviesDatabaseHelper(context, function);

            GetFromServer gfs = new GetFromServer(this, new OnNetworkResponseListener() {
                @Override
                public void onSuccess(ArrayList<Movie> asd) {
                    moviesAdapter = new MoviesAdapter(asd, context, function);
                    recyclerView.setAdapter(moviesAdapter);
                    moviesSize = asd.size();
                    Boolean isInserted = false;
                    for (int i = 0; i < asd.size(); i++) {
                        id = asd.get(i).getId();
                        title = asd.get(i).getTitle();
                        releaseDate = asd.get(i).getReleaseDate();
                        poster_path = asd.get(i).getPoster_path();
                        vote_average = asd.get(i).getVote_average();
                        overview = asd.get(i).getOverview();

                        isInserted = moviesDatabaseHelper.insertMovie(id, title, releaseDate, poster_path, vote_average, overview);
                    }

                }


                @Override
                public void onFailure(Exception e) {
                    Log.d("Error", e.getMessage());
                }
            });
            gfs.execute(function);
        }else {
            moviesDatabaseHelper = new MoviesDatabaseHelper(context, function);
            Cursor result1=moviesDatabaseHelper.getFavoriteMovies();
            Cursor result = moviesDatabaseHelper.getFavoriteMovies();
                if (result.getCount() == 0) {

                    if(function=="popular")
                        function="top_rated";
                    else
                        function="popular";
                    moviesDatabaseHelper = new MoviesDatabaseHelper(context, function);
                    result1 = moviesDatabaseHelper.getFavoriteMovies();
                    if(result1.getCount()==0) {
                        Toast.makeText(context, "Favorited is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            moviesList = new ArrayList<Movie>();
                while (result.moveToNext()) {
                    movie = new Movie(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6));
                    moviesList.add(movie);
                    moviesAdapter = new MoviesAdapter(moviesList, context, function);
                    recyclerView.setAdapter(moviesAdapter);
                }
            moviesList = new ArrayList<Movie>();
                while (result1.moveToNext()){
                    movie1=new Movie(result1.getInt(1), result1.getString(2), result1.getString(3), result1.getString(4), result1.getString(5), result1.getString(6));
                    moviesList.add(movie1);
                    moviesAdapter = new MoviesAdapter(moviesList, context, function);
                    recyclerView.setAdapter(moviesAdapter);
                }
            favorited=false;
            }
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_most_popular) {
            function = "popular";
            updateMovie();
            return true;
        }else if(id == R.id.action_top_rated){
            function = "top_rated";
            updateMovie();
            return true;
        }else if(id == R.id.action_favorited){
            favorited=true;
            updateMovie();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
