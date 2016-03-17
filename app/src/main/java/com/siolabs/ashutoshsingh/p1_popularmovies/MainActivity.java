package com.siolabs.ashutoshsingh.p1_popularmovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.BusProvider;
import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.events.FetchMovieListEvent;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.Movie;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.MovieResponse;
import com.squareup.otto.Bus;

public class MainActivity extends AppCompatActivity implements MovieGridFragment.OnFragmentInteractionListener{

    Fragment gridFragment;
    Fragment detailsFragment;
    Toolbar toolbar;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        title = "Popular Movies";

        gridFragment = new MovieGridFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.main_placeholder, gridFragment);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();


        getSupportActionBar().setTitle(title);


    }

    @Override
    public void onFragmentInteraction(MovieResponse m) {





        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();



        ft.replace(R.id.main_placeholder,MovieDetailsFragment.newInstance(m));

        ft.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(m.getOriginal_title());



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
                BusProvider.getInstance().post(new FetchMovieListEvent.OnLoadingStart("POPULAR"));
                title = "Popular Movies";
                getSupportActionBar().setTitle(title);
                return true;
            case R.id.action_rating:
                title = "Top Rated Movies";
                BusProvider.getInstance().post(new FetchMovieListEvent.OnLoadingStart("RATED"));
                getSupportActionBar().setTitle(title);
                return true;
            case android.R.id.home:
                onBackPressed();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {


        Fragment  current = getSupportFragmentManager().findFragmentById(R.id.main_placeholder);
        if(current instanceof MovieDetailsFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_placeholder,gridFragment).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(title);
            return;
        }




        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}
