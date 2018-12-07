package com.example.billy.androidfinalproject;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.net.URLEncoder;
public class AppToolbar extends AppCompatActivity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.movie_menu_actions, menu);
        MenuItem movieItem = menu.findItem(R.id.action_one);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

            switch (id){
                case R.id.action_one:
                    System.out.println("TESSSSSTTS");

            }


        return true;
    }
}
