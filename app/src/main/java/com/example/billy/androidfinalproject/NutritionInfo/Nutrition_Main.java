package com.example.billy.androidfinalproject.NutritionInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.billy.androidfinalproject.R;

public class Nutrition_Main extends Activity {

    protected static final String ACTIVITY_NAME = "SearchPage";
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_main);

        search = (EditText) findViewById(R.id.searchBar);
        Button sb = (Button) findViewById(R.id.searchButton);

        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = search.getText().toString();
                Intent nextScreen = new Intent(Nutrition_Main.this, search_result.class );
                nextScreen.putExtra("searched", input );
                startActivity(nextScreen);
            }
        });
    }
}
