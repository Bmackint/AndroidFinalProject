package com.example.billy.androidfinalproject.MovieInfo;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.billy.androidfinalproject.R;

public class MovieStats extends Activity {

TextView shortestMovie;
TextView longestMovie;
TextView averageMovie;
TextView averageYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_stats);

        shortestMovie = (TextView) findViewById(R.id.shortest_movie_stat);
        longestMovie = (TextView) findViewById(R.id.longest_movie_stat);
        averageMovie = (TextView) findViewById(R.id.average_movie_stat);
        averageYear = (TextView) findViewById(R.id.average_year_stat);




        MovieDatabaseHelper mdh = new MovieDatabaseHelper(this);
        SQLiteDatabase db = mdh.getReadableDatabase();

       // mdh.getShortestMovie(db);

        shortestMovie.setText("Shortest Movie: " + mdh.getShortestMovie(db) + " minutes");
        longestMovie.setText("Longest Movie: " + mdh.getLongeestMovie(db) + " minutes");
        averageMovie.setText("Average length of saved Movies: " + mdh.averageMovieLength(db) + " minutes");
        //mdh.averageMovieLength(db);
        averageYear.setText("Average Year of saved Movies: " + mdh.averageMovieYear(db));

    }





}
