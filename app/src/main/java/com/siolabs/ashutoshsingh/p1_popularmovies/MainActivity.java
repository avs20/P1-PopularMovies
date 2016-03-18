package com.siolabs.ashutoshsingh.p1_popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.BusProvider;
import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.events.FetchMovieListEvent;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.ListParams;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.Movie;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.MovieResponse;
import com.squareup.otto.Bus;

public class MainActivity extends AppCompatActivity implements MovieGridFragment.OnFragmentInteractionListener{

    Fragment gridFragment;
    Fragment detailsFragment;
    Toolbar toolbar;
    String title;

    boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        determinPaneLayout();

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        title = "Popular Movies";
        getSupportActionBar().setTitle(title);

//        gridFragment = new MovieGridFragment();
//
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        // Replace the contents of the container with the new fragment
//        ft.replace(R.id.movie_grid_fragment, gridFragment);
//        // or ft.add(R.id.your_placeholder, new FooFragment());
//        // Complete the changes added above
//        ft.commit();

    }

    private void determinPaneLayout() {

        FrameLayout detailContainer = (FrameLayout) findViewById(R.id.detailsContainer);
        if(detailContainer != null)
            isTwoPane = true;
        else
            isTwoPane = false;



    }

    @Override
    public void onFragmentInteraction(MovieResponse m) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(isTwoPane){
            ft.replace(R.id.detailsContainer,MovieDetailsFragment.newInstance(m));
            ft.commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(m.getOriginal_title());

            return;
        }else{
            Intent intent = new Intent(this,DetailActivity.class);
            intent.putExtra(AppConstans.ARG_MOVIE,m);
            startActivity(intent);
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_popularity:

                BusProvider.getInstance().post(new FetchMovieListEvent.OnLoadingStart(new ListParams(1,"POPULAR")));
                title = "Popular Movies";
                getSupportActionBar().setTitle(title);
                return true;
            case R.id.action_rating:
                title = "Top Rated Movies";
                BusProvider.getInstance().post(new FetchMovieListEvent.OnLoadingStart(new ListParams(1,"TRENDING")));
                getSupportActionBar().setTitle(title);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}
