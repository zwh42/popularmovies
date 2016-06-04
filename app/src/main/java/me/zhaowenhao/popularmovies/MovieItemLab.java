package me.zhaowenhao.popularmovies;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhaowenhao on 16/6/4.
 */
public class MovieItemLab {
    private static final String TAG = "MovieItemLab";

    private static final String JSON_RESLUT = "results";

    private Context mAppContext;
    private static MovieItemLab sMovieItemLab;

    private ArrayList<MovieItem> mMovieItems;

    private MovieItemLab(Context appContext){
        mAppContext = appContext;
        mMovieItems = new ArrayList<MovieItem>();
    }

    public static MovieItemLab get(Context context){
        if (sMovieItemLab == null){
            sMovieItemLab = new MovieItemLab(context);
        }
        return sMovieItemLab;
    }

    public ArrayList<MovieItem> getMovieItemsFromJSONString(String jsonString){
        try{
            JSONObject json = new JSONObject(jsonString);
            JSONArray resultArray = json.getJSONArray(JSON_RESLUT);
            Log.d(TAG, "getMovieItemsFromJSON: result Array length = " + resultArray.length());

            for (int i = 0; i < resultArray.length(); i++) {
                MovieItem m = new MovieItem(resultArray.getJSONObject(i));
                mMovieItems.add(m);
            }

            for (MovieItem m : mMovieItems){
                Log.d(TAG, "getMovieItemsFromJSONString: movie: " + m);
            }

        }catch (JSONException e){
            Log.e(TAG, "getMovieItemsFromJSON: failed to get JSON array" + jsonString, e);
        }
        return mMovieItems;
    }

    public ArrayList<MovieItem> getMovieItems(){
        return mMovieItems;
    }
}
