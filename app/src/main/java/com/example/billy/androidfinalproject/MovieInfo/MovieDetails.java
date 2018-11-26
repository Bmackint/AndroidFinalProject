package com.example.billy.androidfinalproject.MovieInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billy.androidfinalproject.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieDetails extends Activity {

    private TextView movieTitleTxt;
    private TextView movieYearTxt;
    private TextView movieRatingTxt;
    private TextView movieRuntimeTxt;
    private TextView movieActorsTxt;
    private TextView moviePlotTxt;
    private ImageView movieImageView;

    private Bitmap image;

    private String movieTitle;
    private String movieYear;
    private String movieRating;
    private String movieRuntime;
    private String movieActors;
    private String moviePlot;

    Button removeBtn;
    private static final String imagePath = "/data/data/com.example.billy.androidfinalproject/files/";


    ArrayList<String> ml;
    private int movieIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        ml = new ArrayList<>();

        movieTitleTxt = (TextView) findViewById(R.id.movieTitleTxt);
        movieImageView = (ImageView) findViewById(R.id.movieImageView);
        movieYearTxt = (TextView) findViewById(R.id.movieYearTxt);
        movieRatingTxt = (TextView) findViewById(R.id.movieRatingTxt);
        movieRuntimeTxt = (TextView) findViewById(R.id.movieRuntimeTxt);
        movieActorsTxt = (TextView) findViewById(R.id.movieActorsTxt);
        moviePlotTxt = (TextView) findViewById(R.id.moviePlotTxt);
        removeBtn = (Button) findViewById(R.id.remove_from_favourites_btn);

       //MovieDatabaseHelper db = new MovieDatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            movieTitle = bundle.get("MovieName").toString();
            Log.i("WhatsInTheBundle", bundle.get("MovieName").toString());
            movieIndex = (int) bundle.get("MovieName");


        }

        dbMovieInfo(ml, movieIndex);

        movieTitleTxt.setText("Title: " + ml.get(0));
        movieYearTxt.setText("Year: " + ml.get(1));
        movieRatingTxt.setText("Rating: " + ml.get(2));
   //     movieYearTxt.setText(ml.get(6));
        movieRuntimeTxt.setText("Runtime: " + ml.get(3));
        movieActorsTxt.setText("Actors: " + ml.get(4));
        moviePlotTxt.setText("Plot: " + ml.get(5));
        setMovieValues();

        image = BitmapFactory.decodeFile(imagePath + ml.get(0)+ ".png");
        movieImageView.setImageBitmap(image);

        removeBtn.setOnClickListener((e)->{
            MovieDatabaseHelper moviedbh = new MovieDatabaseHelper(this);
            SQLiteDatabase db = moviedbh.getWritableDatabase();

            db.delete("movies_table", "movie_title=?", new String[]{String.valueOf(ml.get(0))});
            finish();
            Intent nextScreen = new Intent(MovieDetails.this, MyMovies.class);
            startActivity(nextScreen);
           // moviedbh.removeRow(db, movieIndex+1);
        //    db.delete("movies_table", );
         //   db.removeRow
        });

    }
    public void dbMovieInfo(ArrayList<String> al, int index){
        MovieDatabaseHelper moviedbh = new MovieDatabaseHelper(this);
        SQLiteDatabase db = moviedbh.getWritableDatabase();

       // Cursor c = db.rawQuery("SELECT * FROM movies_table WHERE movie_title =" + movieName, null);
        //int movieID = db.rawQuery("SELECT _id FROM movies_table WHERE movie_title=" + ml.get(0).toString(), null);
        Cursor c = db.rawQuery("SELECT * FROM movies_table WHERE _id = " + (index), null);
        if(c.getCount() !=0){
            c.moveToFirst();
            for(int i = 1; i <= 7; i++){
                String resultTitle = c.getString(i);
                al.add(resultTitle);
                Log.i("Column read", resultTitle);
            }
        }
    }

    private void setMovieValues(){
        for(String i: ml){
            Log.i("test", i);
        }
    }

    public boolean fileExistance(String fname){

        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}
