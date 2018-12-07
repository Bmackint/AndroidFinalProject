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
TextView shortestYear;
TextView longestYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_stats);

        shortestMovie = (TextView) findViewById(R.id.shortest_movie_stat);
        longestMovie = (TextView) findViewById(R.id.longest_movie_stat);
        averageMovie = (TextView) findViewById(R.id.average_movie_stat);
        shortestYear = (TextView) findViewById(R.id.shortest_year_stat);
        longestYear = (TextView) findViewById(R.id.longest_year_stat) ;
        averageYear = (TextView) findViewById(R.id.average_year_stat);





        MovieDatabaseHelper mdh = new MovieDatabaseHelper(this);
        SQLiteDatabase db = mdh.getReadableDatabase();

       // mdh.getShortestMovie(db);

        shortestMovie.setText("Shortest Movie: " + mdh.getShortestMovie(db).get(0) + ", " + mdh.getShortestMovie(db).get(1) + " minutes");
        longestMovie.setText("Longest Movie: " + mdh.getLongestMovie(db).get(0) + ", " + mdh.getLongestMovie(db).get(1) + " minutes");
        averageMovie.setText("Average length of saved Movies: " + mdh.averageMovieLength(db) + " minutes");
        //mdh.averageMovieLength(db);
        shortestYear.setText("Oldest Movie: " + mdh.getNewestMovie(db).get(0)+ " ," + mdh.getNewestMovie(db).get(1));
        longestYear.setText("Newest Movie: " + mdh.getOldestMovie(db).get(0)+ " ," + mdh.getOldestMovie(db).get(1));
        averageYear.setText("Average Year of saved Movies: " + mdh.averageMovieYear(db));

    }





}
