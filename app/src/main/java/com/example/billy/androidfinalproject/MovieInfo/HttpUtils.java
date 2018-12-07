package com.example.billy.androidfinalproject.MovieInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    /**
     * returns a bitmap from URL, get(String image) is chained
     * @param url
     * @return bitmap
     */
    public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else

                    return null;
            } catch (Exception e) {
                Log.i("Exception HTTPTS UTILS", e.getMessage());
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

    /**
     * converts String url to URL, chains
     * @param urlString
     * @return
     */
    public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                Log.i("url to string", url.toString());
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
}
