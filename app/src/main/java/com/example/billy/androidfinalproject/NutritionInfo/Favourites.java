package com.example.billy.androidfinalproject.NutritionInfo;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CursorAdapter;

import com.example.billy.androidfinalproject.R;

public class Favourites extends Activity {
    protected static final String ACTIVITY_NAME = "Favourites";
    private ListView list1;

    public SQLiteDatabase db;
    public static final String FOOD = "food";
    public static final String FOOD_ID = "id";
    public static final String FOOD_Label = "label";
    public static final String FOOD_Calories = "calories";
    public static final int VERSION_NUM = 1;
    public static final String DATABASE_NAME = "food.db";
    public static String TABLE_NAME = "myFood";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        list1 = (ListView)findViewById(R.id.faves);

        if(db.isOpen()){
            Log.i(ACTIVITY_NAME, "db open");
        }

        FoodDatabaseHelper fdh = new FoodDatabaseHelper(this);
        db = fdh.getWritableDatabase();
        Cursor foodCursor = db.rawQuery("SELECT * FROM myFood", null);

        FoodCursorAdapter foodAdapter = new FoodCursorAdapter(this, foodCursor);
        list1.setAdapter(foodAdapter);




    }

    public class FoodCursorAdapter extends CursorAdapter{
        public FoodCursorAdapter(Context context, Cursor cursor){
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            return LayoutInflater.from(context).inflate(R.layout.display_row, parent, false);

        }

        @Override
        public void bindView(View view, Context context, Cursor cursor){
            TextView r1 = (TextView)view.findViewById(R.id.row1);
            TextView r2 = (TextView)view.findViewById(R.id.row2);
            TextView r3 = (TextView)view.findViewById(R.id.row3);
            TextView r4 = (TextView)view.findViewById(R.id.row4);

            String rr1 = cursor.getString(cursor.getColumnIndexOrThrow(FOOD));
            String rr2 = cursor.getString(cursor.getColumnIndexOrThrow(FOOD_ID));
            String rr3 = cursor.getString(cursor.getColumnIndexOrThrow(FOOD_Label));
            String rr4 = cursor.getString(cursor.getColumnIndexOrThrow(FOOD_Calories));

            r2.setText(rr1);
            r2.setText(rr2);
            r2.setText(rr3);
            r2.setText(rr4);
        }
    }








    public void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    public void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    public void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    public void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        if(db != null){
            db.close();
        }
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}
