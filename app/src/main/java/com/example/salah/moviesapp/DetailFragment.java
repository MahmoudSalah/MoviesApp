package com.example.albheryyyyuhckfjhdgf.finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albheryyyyuhckfjhdgf.finalproject.Adapters.ReviewsAdapter;
import com.example.albheryyyyuhckfjhdgf.finalproject.Adapters.TrailerAdapter;
import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Movie;
import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Reveiws;
import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Trailer;
import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 17/09/2016.
 */
public class DetailFragment extends Fragment {
    TrailerAdapter trailerAdapter;
    ReviewsAdapter reviewsAdapter;
    MoviesDatabaseHelper moviesDatabaseHelper;
    public static int count=0;
    Movie movie;
    ArrayList<Trailer> trailers;
    ArrayList<Reveiws> reviews;
    Context context;
    TextView textView1;
    ImageView imageView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    LinearListView trailer_listView;
    LinearListView review_listView;
    Button favoriteButton;
    int id;
    String title;
    String releaseDate;
    String poster_path;
    String vote_average;
    String overview;
    String function;
    public DetailFragment() {

    }
    public DetailFragment(Context context) {
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.test, container, false);
        movie = (Movie) getArguments().getSerializable("name");
        function = getArguments().getString("funct");

        textView1 =(TextView) v.findViewById(R.id.titleTextView);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        textView2 = (TextView) v.findViewById(R.id.yearId);
        textView4 = (TextView) v.findViewById(R.id.voteAverageId);
        textView5 =(TextView) v.findViewById(R.id.overview_textView);
        favoriteButton = (Button) v.findViewById(R.id.favoriteId);
        trailer_listView = (LinearListView) v.findViewById(R.id.trailer_listview);
        review_listView = (LinearListView) v.findViewById(R.id.reviews_listview);


        textView1.setText(movie.getTitle());
        String ur = "http://image.tmdb.org/t/p/w185/" + movie.getPoster_path();
        Picasso.with(context).load(ur).into(imageView);
        String[] s=movie.getReleaseDate().split("-");
        textView2.setText(s[0]);
        textView4.setText(movie.getVote_average() + "/10");
        textView5.setText(movie.getOverview());


        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                moviesDatabaseHelper = new MoviesDatabaseHelper(context,function);
                id=movie.getId();
                title=movie.getTitle();
                releaseDate=movie.getReleaseDate();
                poster_path=movie.getPoster_path();
                vote_average=movie.getVote_average();
                overview=movie.getOverview();
                Boolean isUpdated =moviesDatabaseHelper.updateMovie(id+"",title,releaseDate,poster_path,vote_average,overview,1);
                if (isUpdated == true) {
                    Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Data not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });



            if (movie != null) {
                GetTrailerFromServer gtfs = new GetTrailerFromServer(this ,movie.getId() ,new OnNetworkTrailerResponseListener() {
                    @Override
                    public void onSuccess(ArrayList<Trailer> tra) {
                        trailerAdapter = new TrailerAdapter(getActivity(),tra);
                        trailer_listView.setAdapter((ListAdapter) trailerAdapter);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                });
                gtfs.execute();

                trailer_listView.setOnItemClickListener(new LinearListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(LinearListView linearListView, View view, int position, long id) {
                        Trailer trailer = (Trailer) trailerAdapter.getItem(position);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                        startActivity(intent);
                    }
                });
            }
        GetReviewsFromServer grfs = new GetReviewsFromServer(this, movie.getId(), new OnNetworkReviewsResponseListener() {
            @Override
            public void onSuccess(ArrayList<Reveiws> reviews) {
                reviewsAdapter = new ReviewsAdapter(getActivity(), new ArrayList<Reveiws>());
                reviewsAdapter = new ReviewsAdapter(getActivity(), reviews);
                review_listView.setAdapter((ListAdapter)reviewsAdapter);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("Error", e.getMessage());
            }
        });
        grfs.execute();
        return v;
    }
}