package me.zhaowenhao.popularmovies;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhaowenhao on 16/6/4.
 */
public class MovieItem {
    private static final String TAG = "MovieItem";

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_ORIGINAL_TITLE = "original_title";
    private static final String JSON_POSTER_PATH = "poster_path";
    private static final String JSON_OVERVIEW = "overview";
    private static final String JSON_POPULARITY = "popularity";
    private static final String JSON_RATING = "vote_average";
    private static final String JSON_RELEASE_STRING = "release_date";

    private String mId;
    private String mTitle;
    private String mOriginalTitle;
    private String mPosterPath;
    private String mOverview;
    private String mPopularity;
    private String mRating;
    private String mReleaseDate;

    public MovieItem(JSONObject json) throws JSONException {
        mId = json.getString(JSON_ID);
        mTitle = json.getString(JSON_TITLE);
        mOriginalTitle = json.getString(JSON_ORIGINAL_TITLE);
        mPosterPath = json.getString(JSON_POSTER_PATH);
        mOverview = json.getString(JSON_OVERVIEW);
        mPopularity = json.getString(JSON_POPULARITY);
        mRating = json.getString(JSON_RATING);
        mReleaseDate = json.getString(JSON_RELEASE_STRING);

    }

    public String toString(){
        return "MOVIE: id = " + mId + " Title = " +  mTitle;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPopularity() {
        return mPopularity;
    }

    public String getRating() {
        return mRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

}
