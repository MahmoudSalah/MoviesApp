package com.example.albheryyyyuhckfjhdgf.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Reveiws;

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
 * Created by ALBHERYYYYUHCKFJHDGF on 17/09/2016.
 */
public class GetReviewsFromServer extends AsyncTask<String,Void,String> {

    private ArrayAdapter<String> mForecastAdapter;
    ArrayList<Reveiws> reveiws_ArrayList;
    Context context;
    int movieId;
    OnNetworkReviewsResponseListener callBack;
    Exception exception;

    public GetReviewsFromServer(Context context, int movieId, OnNetworkReviewsResponseListener callBack) {
        this.context = context;
        this.movieId=movieId;
        this.callBack=callBack;
    }

    public GetReviewsFromServer(DetailFragment detailFragment, int movieId, OnNetworkReviewsResponseListener callBack) {
        this.context = detailFragment.context;
        this.movieId=movieId;
        this.callBack=callBack;
    }

    ArrayList<Reveiws> call(String[] params) throws JSONException {
        HttpURLConnection urlConnection = null;

        BufferedReader reader = null;
        String forecastJsonStr = null;
        try {
            String baseUrl = "http://api.themoviedb.org/3/movie/"+movieId+"/reviews?";
            String apiKey = "api_key=" + BuildConfig.MOVIE_API_KEY;

            //       //     ///      ////
            URL url = new URL(baseUrl.concat(apiKey));

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
        try {
            reveiws_ArrayList=call(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reveiws_ArrayList.toString();
    }

    @Override
    protected void onPostExecute(String  mstring){
        if(callBack != null) {

//            View view;
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        LayoutInflater inflater = LayoutInflater.from(context);
//            view = inflater.inflate(R.layout.items, null);

//            ImageView imageView = (ImageView) view.findViewById(R.id.imageId);
//            String ur = "http://image.tmdb.org/t/p/w185/" + str;
//            Picasso.with(context).load(ur).into(imageView);
            callBack.onSuccess(reveiws_ArrayList);
        }else{
            callBack.onFailure(exception);
        }

    }

    private ArrayList<Reveiws> getMoviesDataFromJson(String movieJsonStr)
            throws JSONException {
        final String MDB_RESULTS = "results";
        final String MOVIE_ID="id";
        final String MOVIE_AUTHER = "author";
        final String MOVIEW_CONTENT = "content";
        final String MOVIEW_REVIEW_URL = "url";


        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray moviesArray = movieJson.getJSONArray(MDB_RESULTS);

        Reveiws reveiws ;
        reveiws_ArrayList=new ArrayList<>();

        for(int i=0;i<moviesArray.length();i++){
            String id;
            String auther;
            String content;
            String review_url;

            JSONObject dayForecast = moviesArray.getJSONObject(i);

            id = dayForecast.getString(MOVIE_ID);
            auther = dayForecast.getString(MOVIE_AUTHER);
            content = dayForecast.getString(MOVIEW_CONTENT);
            review_url = dayForecast.getString(MOVIEW_REVIEW_URL);

            reveiws = new Reveiws(auther,content,id,review_url);
            reveiws_ArrayList.add(reveiws);

        }


        return reveiws_ArrayList;


    }

}
