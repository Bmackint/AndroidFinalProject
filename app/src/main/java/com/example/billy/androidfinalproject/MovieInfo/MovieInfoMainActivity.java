package com.example.billy.androidfinalproject.MovieInfo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.billy.androidfinalproject.AppToolbar;
import com.example.billy.androidfinalproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//import com.example.billy.androidfinalproject.R;


public class MovieInfoMainActivity extends Activity {
    protected final String ACTIVITY_NAME = "MovieInfoMainActivity";
    private String movieURLString = "http://www.omdbapi.com/?apikey=6c9862c2&r=xml&t=";
    private String posterURL;
    ProgressBar pb;

    Bitmap image;

    private String movieTitle;
    private String movieYear;
    private String movieRating;
    private String movieRuntime;
    private String movieActors;
    private String moviePlot;
   // String iconName;
    String movieName;

   private TextView movieTitleTxt;
   private TextView movieYearTxt;
   private TextView movieRatingTxt;
   private TextView movieRuntimeTxt;
   private TextView movieActorsTxt;
   private TextView moviePlotTxt;
   private ImageView movieImageView;
   private EditText movieSearchET;


    Button movieSearch;
    Button saveMovie;
    Button myMovies;
    Button clearSearch;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info_main_activity);

        movieImageView = (ImageView) findViewById(R.id.movieImageView);
        movieTitleTxt = (TextView) findViewById(R.id.movieTitleTxt);
        movieYearTxt = (TextView) findViewById(R.id.movieYearTxt);
        movieRatingTxt = (TextView) findViewById(R.id.movieRatingTxt);
        movieRuntimeTxt = (TextView) findViewById(R.id.movieRuntimeTxt);
        movieActorsTxt = (TextView) findViewById(R.id.movieActorsTxt);
        moviePlotTxt = (TextView) findViewById(R.id.moviePlotTxt);
        movieSearchET = (EditText) findViewById(R.id.movie_search);


        pb = (ProgressBar) findViewById(R.id.progress_bar);
        movieSearch = (Button) findViewById(R.id.movie_search_button);
        saveMovie = (Button) findViewById(R.id.save_movie_button);
        myMovies = (Button) findViewById(R.id.my_movies_button);
        clearSearch = (Button) findViewById(R.id.clear_movie_search_button);




        //AppToolbar movieToolbar = (AppToolbar) findViewById(R.layout.)
        Toolbar movieToolbar = (Toolbar) findViewById(R.id.movie_toolbar);
       // movieToolbar.set
        setActionBar(movieToolbar);

        movieSearch.setOnClickListener((e)->{
            MovieQuery query= new MovieQuery();
            try {
                movieName = URLEncoder.encode(movieSearchET.getText().toString(), "UTF-8");
            }catch (UnsupportedEncodingException uee){
                uee.printStackTrace();
            }
            query.execute();
        });

        clearSearch.setOnClickListener((e)->{
            clearSearch();
        });

        /**
         * saves movie to database
         */
        saveMovie.setOnClickListener((e)->{

            MovieDatabaseHelper dbHelper = new MovieDatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if(!db.isOpen()) {
                db.isOpen();
            }
                ContentValues newRow = new ContentValues();
                newRow.put("movie_title", movieTitle);
                newRow.put("movie_year", movieYear);
                newRow.put("movie_rating", movieRating);
                newRow.put("movie_runtime", movieRuntime);
                newRow.put("movie_actors", movieActors);
                newRow.put("movie_plot", moviePlot);
                newRow.put("movie_image_URL", posterURL);
                db.insert(dbHelper.getTableName(),null, newRow);
                db.close();

                Toast saveToast = Toast.makeText(this, movieTitle + " saved", Toast.LENGTH_SHORT);
                saveToast.show();
        });
        /**
         * clears search edit text, then starts Mymovies/favourites activity
         */
        myMovies.setOnClickListener((e)->{
            clearSearch();
            Log.i(ACTIVITY_NAME, "User clicked 'my movies'");
            Intent nextScreen = new Intent(MovieInfoMainActivity.this, MyMovies.class);
            startActivity(nextScreen);
        });
    }

    /**
     * async task that quesries website for movie information
     */
    private class MovieQuery extends AsyncTask<String, Integer, String> {


        protected String doInBackground(String... args){
            try {

                URL movieURL = new URL(movieURLString + movieName);
                HttpURLConnection conn = (HttpURLConnection) movieURL.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                InputStream in = (InputStream) conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser parser = factory.newPullParser();
                parser.setInput(in, "UTF-8");

           //     String value;
                /**
                 * finds correct tags and data associated with them
                 */
                while(parser.getEventType() !=XmlPullParser.END_DOCUMENT) {


                    switch (parser.getEventType()) {
                        case XmlPullParser.START_TAG:
                            String name = parser.getName();
                            if(name.equals("movie")){
                                movieTitle = parser.getAttributeValue(null, "title");
                                Log.i("MovieTitle: ", movieTitle);
                                publishProgress(25);
                                movieYear = parser.getAttributeValue(null, "year");
                                Log.i("MovieYear: ", movieYear);
                                publishProgress(50);
                                movieRating = parser.getAttributeValue(null, "imdbRating");
                                publishProgress( 75);
                                Log.i("Movie Rating", movieRating);
                                movieRuntime = parser.getAttributeValue(null, "runtime");
                                Log.i("Runtime", movieRuntime);
                                movieActors = parser.getAttributeValue(null, "actors");
                                moviePlot = parser.getAttributeValue(null, "plot");
                                posterURL = parser.getAttributeValue(null, "poster");
                                Log.i("Movie Poster URL: ", posterURL);
                            }
                            Log.i("read XML tag: ", name);
                            break;
                        case XmlPullParser.TEXT:
                            break;
                    }
                    parser.next();

                }
                /**
                 * checks in movie poster is saved to device, if not saves movie poster
                 */
                if(fileExistance(movieTitle + ".png")){
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(movieTitle + ".png");
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    image = BitmapFactory.decodeStream(fis);
                    fis.close();
                    publishProgress(100);
                    Log.i("MovieImage", "Image file taken from local storage.");
                } else {
                    HttpUtils HTTPUtils = new HttpUtils();
                    image = HTTPUtils.getImage(posterURL);
                    FileOutputStream outputStream = openFileOutput(movieTitle + ".png", Context.MODE_PRIVATE);

                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    publishProgress(100);
                    Log.i("Movie Image: ", "Image has been downloaded.");
                }


            }catch (Exception e){
                Log.i("Exception: ", e.getMessage());
            }
            return "";
        }

        /**
         * updated progress bar as movie loads
         * @param args
         */
        public void onProgressUpdate(Integer ... args){
            pb.setProgress(args[0]);
            pb.setVisibility(View.VISIBLE);
        }

        /**
         * sets all correct values found from xml query to Textviews
         * @param result
         */
        public void onPostExecute(String result) {
            try {
                movieImageView.setImageBitmap(image);
                movieTitleTxt.setText("Title: " + movieTitle);
                movieYearTxt.setText("Year: " + movieYear);
                movieRatingTxt.setText("Rating: " + movieRating);
                movieRuntimeTxt.setText("Movie Runtime: " + movieRuntime);
                movieActorsTxt.setText("Actors: " + movieActors);
                moviePlotTxt.setText("Plot: " + moviePlot);
                pb.setVisibility(View.INVISIBLE);
            }catch (Exception e){
                Log.i("Exception: ", e.getMessage());
            }
        }
    }

    /**
     * used to clear all textview, bitmap image and edit text
     */
    public void clearSearch(){
        movieSearchET.setText("");
        movieImageView.setImageBitmap(null);
        movieTitleTxt.setText("");
        movieYearTxt.setText("");
        movieRatingTxt.setText("");
        movieRuntimeTxt.setText("");
        movieActorsTxt.setText("");
        moviePlotTxt.setText("");
    }
    /**
     * @author Terry E-mail: yaoxinghuo at 126 dot com
     * @version create: 2010-10-21 ??01:40:03
     */




    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        image = BitmapFactory.decodeFile(file.getAbsolutePath());
        Log.i("file path", file.getAbsolutePath());
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
