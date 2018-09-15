package com.example.albheryyyyuhckfjhdgf.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 13/08/2016.
 */
class GetFromServer extends AsyncTask<String,Void,String> {
    private ArrayAdapter<String> mForecastAdapter;
    ArrayList<Movie> temp;
    Context context;
    OnNetworkResponseListener callBack;
    Exception exception;
    String function = "popular";

    public GetFromServer(Context context,OnNetworkResponseListener callBack) {
        this.context = context;
        this.callBack=callBack;
    }

    public GetFromServer(MovieFragment movieFragment, OnNetworkResponseListener error) {
        this.context=movieFragment.context;
        this.callBack=error;
    }


    ArrayList<Movie> call(String[] params) throws JSONException {
        HttpURLConnection urlConnection = null;

        BufferedReader reader = null;
        String forecastJsonStr = null;
        try {
            String baseUrl = "http://api.themoviedb.org/3/movie/";
            String apiKey = "api_key=" + BuildConfig.MOVIE_API_KEY;
            function=params[0];
            URL url = new URL(baseUrl.concat(function+ "?").concat(apiKey));

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            forecastJsonStr = buffer.toString();
            Log.d("JSON", forecastJsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error colsing stream", e);
                }
            }
        }

        return getMoviesDataFromJson(forecastJsonStr);
    }

    @Override
    protected String doInBackground(String... params) {
        String j="";
        try {
            temp=call(params);
        } catch (JSONException e) {
            exception=e;
            e.printStackTrace();
        }
        return j;
    }
    @Override
    protected void onPostExecute(String  mstring){
        if(callBack != null) {
           callBack.onSuccess(temp);
        }else{
            callBack.onFailure(exception);
        }

    }
    private ArrayList<Movie> getMoviesDataFromJson(String movieJsonStr)
            throws JSONException {
        final String MDB_RESULTS = "results";
        final String MDB_ID="id";
        final String MDB_TITLE = "original_title";
        final String MDB_RELEASE_DATE = "release_date";
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_VOTE_AVERAGE = "vote_average";
        final String MDB_OVERVIEW ="overview";


        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray moviesArray = movieJson.getJSONArray(MDB_RESULTS);

        Movie movie ;
        ArrayList<Movie> movies=new ArrayList<>();

        for(int i=0;i<moviesArray.length();i++){
            int id;
            String title;
            String releaseDate;
            String poster_path;
            String vote_average;
            String overview;
            JSONObject dayForecast = moviesArray.getJSONObject(i);

            id = dayForecast.getInt(MDB_ID);
            title = dayForecast.getString(MDB_TITLE);
            releaseDate = dayForecast.getString(MDB_RELEASE_DATE);
            poster_path = dayForecast.getString(MDB_POSTER_PATH);
            vote_average = dayForecast.getString(MDB_VOTE_AVERAGE);
            overview = dayForecast.getString(MDB_OVERVIEW);

            movie = new Movie(id,title,releaseDate,poster_path,vote_average,overview);
            movies.add(movie);
        }


        return movies;


    }

}
