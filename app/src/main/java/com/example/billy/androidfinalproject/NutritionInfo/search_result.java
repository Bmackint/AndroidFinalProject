package com.example.billy.androidfinalproject.NutritionInfo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billy.androidfinalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class search_result extends Activity {

    public static final String FOOD = "food";
    public static final String FOOD_ID = "id";
    public static final String FOOD_Label = "label";
    public static final String FOOD_Calories = "calories";
    public static final int VERSION_NUM = 1;
    public static final String DATABASE_NAME = "food.db";
    public static String TABLE_NAME = "myFood";
    protected static final String ACTIVITY_NAME = "search_result";
    private String search;
    private TextView food;
    private TextView id;
    private TextView labels;
    private TextView energy;
    private Button sb;
    private Button next;

    public String foodId;
    public String label;
    public Double calories;
    public SQLiteDatabase db2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        search = getIntent().getStringExtra("searched");
        food = (TextView)findViewById(R.id.foods);
        id = (TextView)findViewById(R.id.idd);
        labels = (TextView)findViewById(R.id.lab);
        energy = (TextView)findViewById(R.id.en);
        sb = (Button)findViewById(R.id.save);

        final ContentValues cv = new ContentValues();

        FoodQuery q = new FoodQuery();
        q.execute();

        FoodDatabaseHelper fdh = new FoodDatabaseHelper(this);
        db2 = fdh.getWritableDatabase();





        sb.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                cv.put(FOOD, search);
                cv.put(FOOD_ID, foodId);
                cv.put(FOOD_Label, label);
                cv.put(FOOD_Calories, calories);
                db2.insert(TABLE_NAME, "NullableColumn", cv);
                Log.e("Database: ", "After Insertion");


            }
        });



    }

    public class FoodQuery extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void...args){
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://api.edamam.com/api/food-database/parser?app_id=6b54188f&app_key=2638b388afd9eba9f33be1a5176ca5e3&ingr=" + search;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if(jsonStr != null){
                Log.e("Test", "We're In");

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray parsed = jsonObj.getJSONArray("parsed");
                    JSONObject foods = parsed.getJSONObject(0);
                    JSONObject food = foods.getJSONObject("food");
                    label = food.getString("label");
                    foodId = food.getString("foodId");

                    JSONObject nutrients = food.getJSONObject("nutrients");
                    calories = nutrients.getDouble("ENERC_KCAL");


                }catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }



        public void onPostExecute(Void result) {
            super.onPostExecute(result);

            food.setText(search);
            id.setText("Food ID: " + foodId);
            labels.setText("Label: " + label);
            energy.setText("Calories: " + calories + " E/KCal");



        }


    }
}