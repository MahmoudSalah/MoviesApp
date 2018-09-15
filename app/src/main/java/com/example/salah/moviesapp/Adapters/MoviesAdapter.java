package com.example.albheryyyyuhckfjhdgf.finalproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Movie;
import com.example.albheryyyyuhckfjhdgf.finalproject.MovieListener;
import com.example.albheryyyyuhckfjhdgf.finalproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 13/08/2016.
 */
public class MoviesAdapter  extends RecyclerView.Adapter<MyViewHolder> {
    MovieListener movieListener;

    ArrayList<Movie> MoviesArrayList;
    Context context;
    int tempPosition;
    String function;


    public MoviesAdapter(ArrayList<Movie> MoviesArrayList, Context context,String function) {
        this.MoviesArrayList = new ArrayList<>();
        this.MoviesArrayList = MoviesArrayList;
        this.context = context;
        this.function=function;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

            final Movie m = MoviesArrayList.get(position);
            String ur = "http://image.tmdb.org/t/p/w185/" + MoviesArrayList.get(position).getPoster_path();
            Picasso.with(context).load(ur).into(holder.Movies_Image);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieListener = (MovieListener) context;
                    movieListener.setSelectedMovie(m,function);
                }
            });

        }



    @Override
    public int getItemCount() {
        return MoviesArrayList.size();
    }

    void updateAdapter(ArrayList<Movie> asd){
        MoviesArrayList.clear();
        MoviesArrayList=asd;
        notifyDataSetChanged();
    }

    public void setMovieListener(MovieListener mMovieListener){
        movieListener=mMovieListener;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Movies_Image;
    public MyViewHolder(View itemView) {
        super(itemView);
        Movies_Image=(ImageView) itemView.findViewById(R.id.imageId);
    }

}


