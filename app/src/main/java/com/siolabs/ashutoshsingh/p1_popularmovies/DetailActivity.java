package com.siolabs.ashutoshsingh.p1_popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.siolabs.ashutoshsingh.p1_popularmovies.models.MovieResponse;

public class DetailActivity extends AppCompatActivity {

    MovieResponse response;
    MovieDetailsFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        handleIntent(getIntent());
        getSupportActionBar().setTitle(response.getOriginal_title());
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    private void handleIntent(Intent intent) {

        response = getIntent().getParcelableExtra(AppConstans.ARG_MOVIE);
        fragment = MovieDetailsFragment.newInstance(response);

        getSupportFragmentManager().beginTransaction().replace(R.id.movie_details_container,fragment)
                .commit();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
