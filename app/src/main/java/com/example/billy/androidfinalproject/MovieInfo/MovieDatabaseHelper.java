package com.example.billy.androidfinalproject.MovieInfo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "Movies.db";
    protected static final String TABLE_NAME = "movies_table";
    public static final String KEY_ID = "_id";
    public static final String KEY_MOVIES = "movie_title";
    public static final String KEY_YEAR = "movie_year";
    public static final String KEY_RATING = "movie_rating";
    public static final String KEY_RUNTIME ="movie_runtime";
    public static final String KEY_MAIN_ACTORS="movie_actors";
    public static final String KEY_PLOT="movie_plot";
    public static final String KEY_IMAGE_URL = "movie_image_URL";
    protected static final int VERSION_NUM = 2;



    public MovieDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MOVIES + " TEXT," +
                KEY_YEAR + " TEXT," +
                KEY_RATING + " TEXT," +
                KEY_RUNTIME + " TEXT," +
                KEY_MAIN_ACTORS + "  TEXT," +
                KEY_PLOT + " TEXT," +
                KEY_IMAGE_URL + " TEXT" +
                ");");
        Log.i("MovieDatabaseHelper", "calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("MovieDatabaseHelper", "calling onUpgrade");
    }

    public void onDowngrade(SQLiteDatabase db, int newVersion, int oldVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("MovieDatabaseHelper", "calling onDowngrade");
    }
    public String getTableName(){
        return this.TABLE_NAME;
    }

    public void removeRow(SQLiteDatabase db, int movieId){
        db.execSQL("DELETE * FROM movies_table WHERE name LIKE _id = " + movieId);


    }

    public ArrayList getShortestMovie(SQLiteDatabase db){
        ArrayList lengths = new ArrayList();
        Cursor c = db.rawQuery("SELECT movie_title, movie_runtime FROM movies_table", null);
        int movieTitleRow = c.getColumnIndex("movie_title");
        int movieRuntimeRow = c.getColumnIndex("movie_runtime");
        for(c.moveToFirst();!c.isAfterLast(); c.moveToNext()){
            int lenInt;
            int lenInt2;
            if(lengths.isEmpty()){

                lengths.add(0, c.getString(movieTitleRow));
                Log.i("addMovieTitle", c.getString(movieTitleRow));
                lenInt = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
                lengths.add(1, lenInt);

                Log.i("addRuntime", lengths.toString());
            }
            lenInt2 = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
            if(lenInt2 < (int) lengths.get(1)){
                lengths.clear();
                lengths.add(0, c.getString(movieTitleRow));
                lengths.add(1, lenInt2);
                lenInt = lenInt2;
                Log.i("Comparison", lengths.toString());

            }
        }
        return lengths;
    }
    public ArrayList getLongestMovie(SQLiteDatabase db){
        ArrayList lengths = new ArrayList();
        Cursor c = db.rawQuery("SELECT movie_title, movie_runtime FROM movies_table", null);
        int movieTitleRow = c.getColumnIndex("movie_title");
        int movieRuntimeRow = c.getColumnIndex("movie_runtime");
        for(c.moveToFirst();!c.isAfterLast(); c.moveToNext()){
            int lenInt;
            int lenInt2;
            if(lengths.isEmpty()){

                lengths.add(0, c.getString(movieTitleRow));
                Log.i("addMovieTitle", c.getString(movieTitleRow));
                lenInt = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
                lengths.add(1, lenInt);

                Log.i("addRuntime", lengths.toString());
            }
            lenInt2 = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
            if(lenInt2 > (int) lengths.get(1)){
                lengths.clear();
                lengths.add(0, c.getString(movieTitleRow));
                lengths.add(1, lenInt2);
                lenInt = lenInt2;
                Log.i("Comparison", lengths.toString());

            }
        }
        return lengths;
    }
    public int averageMovieLength(SQLiteDatabase db){
        ArrayList lengths = new ArrayList();
        int totalLen = 0;
        int averageLen = 0;
        Cursor c = db.rawQuery("SELECT movie_runtime FROM movies_table", null);
        int movieRuntimeRow = c.getColumnIndex("movie_runtime");
        int numberOfRows=0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            numberOfRows = numberOfRows++;
            int lenInt = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
            lengths.add(lengths.size(), lenInt);
            Log.i("AveragelenArray", lengths.toString());

        }
        for(int i = 0; i < lengths.size(); i++){
            totalLen = totalLen + (int) lengths.get(i);
        }
        return totalLen/lengths.size();
    }

    public int averageMovieYear(SQLiteDatabase db){
        ArrayList lengths = new ArrayList();
        int totalLen = 0;
        int averageLen = 0;
        Cursor c = db.rawQuery("SELECT movie_year FROM movies_table", null);
        int movieRuntimeRow = c.getColumnIndex("movie_year");
        int numberOfRows=0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            numberOfRows = numberOfRows++;
            int lenInt = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
            lengths.add(lengths.size(), lenInt);
            Log.i("AverageYearArray", lengths.toString());

        }
        for(int i = 0; i < lengths.size(); i++){
            totalLen = totalLen + (int) lengths.get(i);
        }
        return totalLen/lengths.size();
    }

    public ArrayList getNewestMovie(SQLiteDatabase db){
        ArrayList lengths = new ArrayList();
        Cursor c = db.rawQuery("SELECT movie_title, movie_year FROM movies_table", null);
        int movieTitleRow = c.getColumnIndex("movie_title");
        int movieRuntimeRow = c.getColumnIndex("movie_year");
        for(c.moveToFirst();!c.isAfterLast(); c.moveToNext()){
            int lenInt;
            int lenInt2;
            if(lengths.isEmpty()){

                lengths.add(0, c.getString(movieTitleRow));
                Log.i("addMovieTitle", c.getString(movieTitleRow));
                lenInt = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
                lengths.add(1, lenInt);

                Log.i("addRuntime", lengths.toString());
            }
            lenInt2 = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
            if(lenInt2 < (int) lengths.get(1)){
                lengths.clear();
                lengths.add(0, c.getString(movieTitleRow));
                lengths.add(1, lenInt2);
                lenInt = lenInt2;
                Log.i("Comparison", lengths.toString());

            }
        }
        return lengths;
    }
    public ArrayList getOldestMovie(SQLiteDatabase db){
        ArrayList lengths = new ArrayList();
        Cursor c = db.rawQuery("SELECT movie_title, movie_year FROM movies_table", null);
        int movieTitleRow = c.getColumnIndex("movie_title");
        int movieRuntimeRow = c.getColumnIndex("movie_year");
        for(c.moveToFirst();!c.isAfterLast(); c.moveToNext()){
            int lenInt;
            int lenInt2;
            if(lengths.isEmpty()){

                lengths.add(0, c.getString(movieTitleRow));
                Log.i("addMovieTitle", c.getString(movieTitleRow));
                lenInt = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
                lengths.add(1, lenInt);

                Log.i("addRuntime", lengths.toString());
            }
            lenInt2 = Integer.parseInt(c.getString(movieRuntimeRow).replaceAll("[^0-9]", ""));
            if(lenInt2 > (int) lengths.get(1)){
                lengths.clear();
                lengths.add(0, c.getString(movieTitleRow));
                lengths.add(1, lenInt2);
                lenInt = lenInt2;
                Log.i("Comparison", lengths.toString());

            }
        }
        return lengths;
    }

}
