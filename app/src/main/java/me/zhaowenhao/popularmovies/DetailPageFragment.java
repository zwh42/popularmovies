package me.zhaowenhao.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by zhaowenhao on 16/6/7.
 */
public class DetailPageFragment extends Fragment {
    private static final String TAG = "DetailPageFragment";

    public static final String MOVIE_ID = "MOVIE_ID";
    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    private MovieItem mMovieItem;
    private TextView mTitle;
    private ImageView mPoster;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mPopularity;
    private TextView mOverview;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String id =  getActivity().getIntent().getStringExtra(MOVIE_ID);
        mMovieItem = MovieItemLab.get(getActivity()).getMovieById(id);
        //Toast.makeText(getActivity().getApplicationContext(), mMovieItem.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.detail_page_fragment, parent, false);
        mTitle = (TextView) v.findViewById(R.id.title);
        mPoster = (ImageView) v.findViewById(R.id.poster);
        mReleaseDate = (TextView) v.findViewById(R.id.release_date);
        mRating = (TextView) v.findViewById(R.id.rating);
        mPopularity = (TextView) v.findViewById(R.id.popularity);
        mOverview = (TextView) v.findViewById(R.id.overview);

        String posterUrl = MOVIE_POSTER_BASE_URL + mMovieItem.getPosterPath();
        Log.d(TAG, "getView: poser path " + posterUrl);
        Picasso.with(getActivity().getApplicationContext()).load(posterUrl).into(mPoster);

        mTitle.setText(mMovieItem.getTitle());
        mReleaseDate.setText(mMovieItem.getReleaseDate());
        mRating.setText("rating: " + mMovieItem.getRating() + "/10");
        mPopularity.setText("popularity: " + mMovieItem.getPopularity() + "/100");
        mOverview.setText(mMovieItem.getOverview());

        return v;
    }

}
