package com.example.billy.androidfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.billy.androidfinalproject.MovieInfo.MovieInfoMainActivity;

public class MainActivity extends Activity {
    protected final String ACTIVITY_NAME = "MainActivity";
    Button movieInfoButton;
    Button nutritionInfoButton;
    Button newsInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieInfoButton = (Button) findViewById(R.id.movie_info_app);
        nutritionInfoButton = (Button) findViewById(R.id.nutrition_info_app);
        newsInfoButton = (Button) findViewById(R.id.nutrition_info_app);

        movieInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(MainActivity.this, MovieInfoMainActivity.class);
                startActivity(nextScreen);
            }
        });
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
