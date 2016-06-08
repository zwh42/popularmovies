package me.zhaowenhao.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by zhaowenhao on 16/6/4.
 */
public class MovieDBFetcher {
    public static final String TAG = "MovieDBFetcher";

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie";
    private static final String API_KEY = PrivateConfig.API_KEY;
    private static final String POPULAR = "popular";

    byte[] getUrlBytes(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            Log.d(TAG, "getUrlBytes: connection.getResponseCode() = " + connection.getResponseCode());
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrl(String urlString) throws IOException {
        return new String(getUrlBytes(urlString));
    }

    public ArrayList<MovieItem> fetchItems(Context appContext) {
        String url = Uri.parse(BASE_URL).buildUpon().appendPath(POPULAR).appendQueryParameter("api_key", API_KEY).build().toString();
        Log.d(TAG, "fetchItems, url =  " + url);
        ArrayList<MovieItem> movieItems = new ArrayList<MovieItem>();
        try {
            String jsonString = getUrl(url);
            Log.d(TAG, "fetchItems: fetched json string:" + jsonString);
            movieItems = MovieItemLab.get(appContext).getMovieItemsFromJSONString(jsonString);
            for (MovieItem m : movieItems){
                Log.d(TAG, "fetchItems: movie: " + m.getTitle());
            }
        }catch (IOException ioe){
            Log.e(TAG, "fetchItems: failed to fetch movie json", ioe);
        }

        return movieItems;


    }
}