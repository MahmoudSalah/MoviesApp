package com.example.albheryyyyuhckfjhdgf.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 22/09/2016.
 */
public class MoviesDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Movies.db";
    public static final String TABLE_NAME_MOST_POPULAR = "Movies_Table_MOST_POPULAR";
    public static final String TABLE_NAME_TOP_RATED = "Movies_Table_HIGHEST_RATED";
    public static final String TABLE_NAME_ISFAVORITED= "IsFavorite_Table";

    String function;

    public static String COL_ID ="id";
    public static final String MOVIE_ID_COL = "Movie_id";
    public static final String TITLE_COL = "title";
    public static final String RELEASEDATE_COL = "releaseDate";
    public static final String POSTER_COL = "poster_path";
    public static final String VOTE_COL = "vote_average";
    public static final String OVERVIEW_COL = "overview";
    public static final String ISFAVORITED = "isFavorite";

    public MoviesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public MoviesDatabaseHelper(Context context,String function) {
        super(context, DATABASE_NAME, null, 1);
        this.function=function;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_MOVIES_TABLE_MOST_POPULAR = "CREATE TABLE "+ TABLE_NAME_MOST_POPULAR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MOVIE_ID_COL +" INTEGER NOT NULL, " + TITLE_COL + " TEXT NOT NULL, " + RELEASEDATE_COL + " TEXT, " +
                POSTER_COL + " TEXT, "+ VOTE_COL + " TEXT, " + OVERVIEW_COL + " TEXT, " + ISFAVORITED + " INTEGER);" ;

        final String CREATE_MOVIES_TABLE_HIGHEST_RATED = "CREATE TABLE "+ TABLE_NAME_TOP_RATED + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MOVIE_ID_COL +" INTEGER NOT NULL, " + TITLE_COL + " TEXT NOT NULL, " + RELEASEDATE_COL + " TEXT, " +
                POSTER_COL + " TEXT, "+ VOTE_COL + " TEXT, " + OVERVIEW_COL + " TEXT, " + ISFAVORITED + " INTEGER);" ;

        final String CREATE_ISFAVORITED_TABLE = "CREATE TABLE " + TABLE_NAME_ISFAVORITED +  " (" +
                MOVIE_ID_COL + " INTEGER , " + ISFAVORITED + " INTEGER);";

        db.execSQL(CREATE_MOVIES_TABLE_MOST_POPULAR);
        db.execSQL(CREATE_MOVIES_TABLE_HIGHEST_RATED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(function == "top_rated") {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TOP_RATED);
            onCreate(db);
        }
        else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MOST_POPULAR);
            onCreate(db);
        }
    }

    public Boolean insertMovie(int id , String title , String release_date , String poster_path ,String vote , String overview){
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_ID_COL, id);
        contentValues.put(TITLE_COL, title);
        contentValues.put(RELEASEDATE_COL, release_date);
        contentValues.put(POSTER_COL, poster_path);
        contentValues.put(VOTE_COL, vote);
        contentValues.put(OVERVIEW_COL, overview);
        contentValues.put(ISFAVORITED,0);

        if(function == "top_rated") {
            result = db.insert(TABLE_NAME_TOP_RATED, null, contentValues);

        }
        else {
            result = db.insert(TABLE_NAME_MOST_POPULAR, null, contentValues);
        }
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getMovies(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result;
        if(function == "top_rated") {
            result = db.rawQuery("select * from " + TABLE_NAME_TOP_RATED, null);
        }else {
            result = db.rawQuery("select * from " + TABLE_NAME_MOST_POPULAR, null);
        }
        return result;
    }

    public Cursor getFavoriteMovies(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result;
        if(function == "top_rated") {
            result = db.rawQuery("select * from " + TABLE_NAME_TOP_RATED +" where " + ISFAVORITED +"==1 Group by "+MOVIE_ID_COL+";", null);
        }else {
            result = db.rawQuery("select * from " + TABLE_NAME_MOST_POPULAR +" where " + ISFAVORITED +"==1 Group by "+MOVIE_ID_COL+";", null);
        }
        return result;
    }

    public Boolean updateMovie(String id , String title , String release_date , String poster_path ,String vote , String overview , int favorited){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_ID_COL, id);
        contentValues.put(TITLE_COL, title);
        contentValues.put(RELEASEDATE_COL, release_date);
        contentValues.put(POSTER_COL, poster_path);
        contentValues.put(VOTE_COL, vote);
        contentValues.put(OVERVIEW_COL, overview);
        contentValues.put(ISFAVORITED, favorited);
        if(function == "top_rated") {
            db.update(TABLE_NAME_TOP_RATED,contentValues,MOVIE_ID_COL +" = ? ",new String[]{id});
            return true;
        }
        else {
            db.update(TABLE_NAME_MOST_POPULAR,contentValues,MOVIE_ID_COL +" = ? ",new String[]{id});
            return true;
        }

    }

}
