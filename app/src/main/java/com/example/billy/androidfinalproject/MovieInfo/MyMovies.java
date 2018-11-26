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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.billy.androidfinalproject.MainActivity;
import com.example.billy.androidfinalproject.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyMovies extends Activity{
    private String ACTIVITY_NAME = "MyMovies";
    private ListView movieView;
    protected ArrayList<String> movieList;
    private ArrayList<String> posterImageURL;
    private TextView movieTitle;
    private ImageView movieImage;

    int movieID;
    Button movieStatsBtn;

    private Bitmap image2;
    private String movieURLString;
    private static String posterURL = "amazon.com/images/M/MV5BMWM0ZjY5ZjctODNkZi00Nzk0LWE1ODUtNGM4ZDUyMzUwMGYwXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_movies);

        movieView = (ListView) findViewById(R.id.my_movies_listview);
        movieList = new ArrayList<>();
        posterImageURL = new ArrayList<>();
        movieTitle = (TextView) findViewById(R.id.movie_title_row);
        movieImage = (ImageView) findViewById(R.id.movie_image_row);
        movieStatsBtn = findViewById(R.id.my_movies_stats);


        movieStatsBtn.setOnClickListener((e)->{
            Intent nextScreen = new Intent(MyMovies.this, MovieStats.class);
            startActivity(nextScreen);
        });



        HttpUtils movieImageUtil = new HttpUtils();

        MovieAdapter movieAdapter = new MovieAdapter(this);
        movieView.setAdapter(movieAdapter);

        movieView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //
                String movieName = movieList.get(position);
                int movieID = getID(movieName);

                Intent nextScreen = new Intent(MyMovies.this, MovieDetails.class);

               // nextScreen.putExtra("MovieName", movieList.get((int)movieView.getItemIdAtPosition(position)));
                nextScreen.putExtra("MovieName", movieID);

               // nextScreen.putExtra("movieList", movieList);
                //nextScreen.putExtra("arrayPosition", position);
                finish();
                startActivity(nextScreen);
                //startActivityForResult(nextScreen, 50);
            }
        });


        String movie_title = "movie_title";
        String movie_image_URL = "movie_image_URL";

        dbColumnToList(movieList, movie_title);
        dbColumnToList(posterImageURL, movie_image_URL);
    }

    public int getID(String title){
       // int movieID = 0;
        MovieDatabaseHelper moviedbh = new MovieDatabaseHelper(this);
        SQLiteDatabase db = moviedbh.getReadableDatabase();
        String result = " ";


        Cursor c = db.rawQuery("SELECT _id, movie_title FROM movies_table WHERE movie_title=?", new String[] {title}, null);

        int idRow = c.getColumnIndex("_id");
        int titleRow = c.getColumnIndex("movie_title");
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            Log.i("titleRow:  ", c.getString(titleRow));
            if(c.getString(titleRow).equals(title)) {
                result = result + c.getString(idRow); // + " " + c.getString(titleRow);
                //movieID = idRow;

                Log.i("rowReads", result + "66666666666");
                Log.i("idRow", c.getString(idRow));
                movieID = movieID + idRow;
                break;
                //return idRow;
            }
        }
        //return idRow;
        return Integer.parseInt(c.getString(idRow));
    }
    public void dbColumnToList(ArrayList<String> al, String columnName){
        MovieDatabaseHelper moviedbh = new MovieDatabaseHelper(this);
        SQLiteDatabase db = moviedbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM movies_table", null);
        int numInstances = c.getCount();
        int titleColumn = c.getColumnIndex(columnName);
        Log.i(ACTIVITY_NAME,  Integer.toString(numInstances) + " rows in db, " + "and columns: " + moviedbh.KEY_ID + ", " + moviedbh.KEY_MOVIES );
        if(c.getCount() !=0){
            c.moveToFirst();
            for(int i = 1; i <= c.getCount(); i++){
                String resultTitle = c.getString(titleColumn);
                Log.i(ACTIVITY_NAME, "Cursor's column count: " + c.getColumnCount() + "     current row: " + c.getPosition() + resultTitle.toString());
                al.add(resultTitle);
                c.moveToNext();
            }

        }

    }

    protected class MovieAdapter extends ArrayAdapter<String> {
        MovieAdapter(Context context) {
            super(context, 0);
        }
        public int getCount(){
            return movieList.size();
        }
        public String getItem(int position){

            return movieList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = MyMovies.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.movie_row, null);
            TextView movieTitle = (TextView) result.findViewById(R.id.movie_title_row);
            ImageView moviePoster = (ImageView) result.findViewById(R.id.movie_image_row);
            movieTitle.setText(getItem(position));

           FileInputStream fis = null;
            try {

                fis = openFileInput( movieList.get(position) + ".png");
                Log.i("currentmoviename", movieList.get(position).toString());
                Bitmap image3 = BitmapFactory.decodeStream(fis);
                moviePoster.setImageBitmap(image3);
            }catch (FileNotFoundException e){
                Log.i("ImageNotFound", e.getMessage());
            }

            return result;
        }
    }



    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
    public void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    public void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onCreate()");
    }
    public void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    public void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy");
    }
}
