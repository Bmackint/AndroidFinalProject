package com.example.billy.androidfinalproject.MovieInfo;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.billy.androidfinalproject.R;



public class MovieMenu extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu_actions, menu);

        return true;
    }
}
