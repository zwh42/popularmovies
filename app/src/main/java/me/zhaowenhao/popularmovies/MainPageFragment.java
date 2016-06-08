package me.zhaowenhao.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by zhaowenhao on 16/6/4.
 */
public class MainPageFragment extends Fragment{
    private static final String TAG = "MainPageFragment";
    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    GridView mGridView;
    ArrayList<MovieItem> mMovieItems;
    MovieItemAdapter mMovieItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mMovieItems = MovieItemLab.get(getActivity()).getMovieItems();
        startFetchMovieItems();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_main_page,container,false);
        mGridView = (GridView) v.findViewById(R.id.gridView);
        setupAdapter();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick, pos: " + position);
                MovieItem clickedMovie = (MovieItem) parent.getItemAtPosition(position);
                //Toast.makeText(getActivity().getApplicationContext(), "position " + position + clickedMovie, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), DetailPageActivity.class);
                i.putExtra(DetailPageFragment.MOVIE_ID, clickedMovie.getId());
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main_page, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_most_popular:
                Collections.sort(mMovieItems, compareByPopularity);
                mMovieItemAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_top_rated:
                Collections.sort(mMovieItems, compareByRating);
                mMovieItemAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateUI(){

    }


    public Comparator<MovieItem> compareByPopularity = new Comparator<MovieItem>(){
        public int compare(MovieItem movieItem1, MovieItem movieItem2){
            return Double.compare(Double.parseDouble(movieItem2.getPopularity()),Double.parseDouble(movieItem1.getPopularity()));
        }
    };

    public  Comparator<MovieItem> compareByRating = new Comparator<MovieItem>(){
        public int compare(MovieItem movieItem1, MovieItem movieItem2){
            return Double.compare(Double.parseDouble(movieItem2.getRating()),Double.parseDouble(movieItem1.getRating()));
        }
    };

    private void startFetchMovieItems(){
        new FetchMovieItemsTask().execute();
    }

    private void setupAdapter(){
        if (getActivity() == null || mGridView == null){
            Log.e(TAG, "setupAdapter: no active Activity or View found");
            return;
        }

        if (mMovieItems != null){
            Log.d(TAG, "setupAdapter: mMovieItems length " + mMovieItems.size());
            mMovieItemAdapter = new MovieItemAdapter(mMovieItems);
            mGridView.setAdapter(mMovieItemAdapter);
        } else {
            Log.d(TAG, "setupAdapter: no movie items!");
            mGridView.setAdapter(null);
        }
    }

    private class MovieItemAdapter extends ArrayAdapter<MovieItem>{
        public MovieItemAdapter(ArrayList<MovieItem> items){
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.movie_item, parent, false);
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_poster);
            //String posterUrl = Uri.parse(MOVIE_POSTER_BASE_URL).buildUpon().appendPath(getItem(position).getPosterPath()).toString();
            String posterUrl = MOVIE_POSTER_BASE_URL + getItem(position).getPosterPath();
            Log.d(TAG, "getView: poser path " + posterUrl);
            Picasso.with(getContext()).load(posterUrl).into(imageView);


            return convertView;
        }
    }

    private class FetchMovieItemsTask extends AsyncTask<Void, Void, ArrayList<MovieItem>>{

        @Override
        protected ArrayList<MovieItem> doInBackground(Void ...params){
            Log.d(TAG, "doInBackground: running!");
            ArrayList<MovieItem> items = new MovieDBFetcher().fetchItems(getActivity().getApplicationContext());

            if (items == null){
                Log.e(TAG, "doInBackground: nothing received");
                return null;
            }

            for(MovieItem m : items){
                Log.d(TAG, "doInBackground: fetched: " + m.getTitle());
            }

            Log.d(TAG, "doInBackground: fetch finished!");
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> items){
            mMovieItems = items;
            for (MovieItem m : items){
                Log.d(TAG, "onPostExecute: movie:" + m.getTitle());
            }
            Log.d(TAG, "onPostExecute: executing!");
            setupAdapter();
        }
    }

}
