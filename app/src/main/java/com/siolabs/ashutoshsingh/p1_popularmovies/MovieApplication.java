package com.siolabs.ashutoshsingh.p1_popularmovies;

import android.app.Application;

import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.BusProvider;
import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.MovieManager;
import com.squareup.otto.Bus;

/**
 * Created by ashutoshsingh on 16-03-2016.
 */
public class MovieApplication extends Application {

    private MovieManager mMovieManager;
    private Bus mBus =  BusProvider.getInstance();;

    @Override
    public void onCreate() {
        super.onCreate();

        mMovieManager = new MovieManager(this,mBus);
        mBus.register(mMovieManager);
        mBus.register(this);
    }
}
