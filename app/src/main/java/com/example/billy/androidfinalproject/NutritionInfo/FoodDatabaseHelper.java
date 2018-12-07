package com.example.billy.androidfinalproject.NutritionInfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FoodDatabaseHelper extends SQLiteOpenHelper {
    public static final String FOOD = "food";
    public static final String FOOD_ID = "id";
    public static final String FOOD_Label = "label";
    public static final String FOOD_Calories = "calories";
    public static final int VERSION_NUM = 1;
    public static final String DATABASE_NAME = "food.db";
    public static String TABLE_NAME = "myFood";
    public FoodDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + FOOD + " TEXT, " + FOOD_ID + " TEXT, " + FOOD_Label + " TEXT, " + FOOD_Calories + " TEXT);");
        Log.i("FoodDatabaseHelper", "Calling onCreate");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i("FoodDatabaseHelper", "Calling on Upgrade, oldVersion =" + oldVer + "newVersion =" + newVer);

    }
}

