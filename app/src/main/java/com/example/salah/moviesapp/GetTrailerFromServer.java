package com.example.albheryyyyuhckfjhdgf.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Trailer;

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
public class GetTrailerFromServer extends AsyncTask<String,Void,String> {

    private ArrayAdapter<String> mForecastAdapter;
    ArrayList<Trailer> trailers_ArrayList;
    Context context;
    int movieId;
    OnNetworkTrailerResponseListener callBack;
    Exception exception;

    public GetTrailerFromServer(Context context,int movieId,OnNetworkTrailerResponseListener callBack) {
        this.context = context;
        this.movieId=movieId;
        this.callBack=callBack;
    }
    public GetTrailerFromServer(DetailFragment detailFragment,int movieId,OnNetworkTrailerResponseListener callBack) {
        this.context = detailFragment.context;
        this.movieId=movieId;
        this.callBack=callBack;
    }

    public GetTrailerFromServer(Context context) {
        this.context=context;
    }

    ArrayList<Trailer> call(String[] params) throws JSONException {
        HttpURLConnection urlConnection = null;

        BufferedReader reader = null;
        String forecastJsonStr = null;
        try {
            String baseUrl = "http://api.themoviedb.org/3/movie/"+movieId+"/videos?";
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
            trailers_ArrayList=call(params);
            int asd=trailers_ArrayList.size();
        } catch (JSONException e) {
            exception=e;
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String  mstring){
        if(callBack != null) {

            callBack.onSuccess(trailers_ArrayList);
        }else{
            callBack.onFailure(exception);
        }

    }

    private ArrayList<Trailer> getMoviesDataFromJson(String movieJsonStr)
            throws JSONException {
        final String TR_RESULTS = "results";
        final String TR_NAME="name";
        final String TR_KEY = "key";


        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray moviesArray = movieJson.getJSONArray(TR_RESULTS);

        Trailer trailer ;
        trailers_ArrayList=new ArrayList<>();

        for(int i=0;i<moviesArray.length();i++){
            String name;
            String key;

            JSONObject dayForecast = moviesArray.getJSONObject(i);

            name = dayForecast.getString(TR_NAME);
            key = dayForecast.getString(TR_KEY);


            trailer = new Trailer(key,name);
            trailers_ArrayList.add(trailer);
//            result.add(resultStrs);
        }


        return trailers_ArrayList;


    }

}
