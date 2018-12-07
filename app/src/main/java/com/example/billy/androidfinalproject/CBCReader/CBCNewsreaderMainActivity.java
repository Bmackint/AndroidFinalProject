package com.example.billy.androidfinalproject.CBCReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.billy.androidfinalproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CBCNewsreaderMainActivity extends Activity {

    private String ACTIVITY_NAME = "CBCNewsReader";
    public TextView newsItemOne;
    public TextView storyOne;
    private ProgressBar progBar;
    private Button thisButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbcnewsreader_main);
        progBar = (ProgressBar)findViewById(R.id.progressBar);
        progBar.setVisibility(View.VISIBLE);
        newsItemOne=findViewById(R.id.newsItem1);
        storyOne=findViewById(R.id.storyOne);
        thisButton=findViewById(R.id.saveArticle);
        NewsHeadline howDidIForgetThis = new NewsHeadline();
        howDidIForgetThis.execute();
    }

    private class NewsHeadline extends AsyncTask<String, Integer, String> {
        private String headline;
        private String description;
        @Override
        protected String doInBackground(String ... args) {


            try {
                URL url = new URL("https://www.cbc.ca/cmlink/rss-world");
                HttpURLConnection newsConnection = (HttpURLConnection) url.openConnection();
                newsConnection.setReadTimeout(10000);
                newsConnection.setConnectTimeout(15000);
                newsConnection.setRequestMethod("GET");
                newsConnection.setDoInput(true);
                InputStream answer = newsConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(answer, "UTF-8");

                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                    switch (xpp.getEventType()) {

                        case XmlPullParser.START_TAG:
                            String name = xpp.getName();
                            if (name.equals("item")) {
                                headline = xpp.getAttributeValue(null, "title");
                                description = xpp.getAttributeValue(null, "p");
                                //publishProgress(50);
                            }
                            Log.i("read XML tag:", name);
                            break;

                        case XmlPullParser.TEXT:
                            break;
                    }

                    xpp.next();
                }

            } catch (Exception e) {
                Log.i("Exception", e.getMessage());
            }

            return "";


        }
        public void onPostExecute(String result)
        {
            newsItemOne.setText(headline);
            storyOne.setText(description);

        }
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
