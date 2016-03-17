package com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus;

import android.content.Context;
import android.util.Log;

import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.events.FetchMovieDetailsEvent;
import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.events.FetchMovieListEvent;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.ApiResponse;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.Movie;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.MovieResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ashutoshsingh on 16-03-2016.
 */
public class MovieManager {

    private Context mContext;
    private Bus mBus;
    private MovieClient mMovieClient;


    public MovieManager(Context mContext, Bus mBus) {
        this.mContext = mContext;
        this.mBus = mBus;
        mMovieClient = MovieClient.getClient(mContext);
    }


    @Subscribe
    public void onLoadMovies(FetchMovieListEvent.OnLoadingStart onLoadingStart){

        if(onLoadingStart.getRequest().equalsIgnoreCase("POPULAR"))
            mMovieClient.getMovieService()
                    .getPopularMovies()
                    .enqueue(new MyResponse());
        else
            mMovieClient.getMovieService()
                    .getTopRatedMovies()
                    .enqueue(new MyResponse());
    }

    @Subscribe
    public void onLoadMovieDetails(FetchMovieDetailsEvent.OnLoadingStart onLoadingStart){
        mMovieClient.getMovieService()
                .getMovieDetails(onLoadingStart.getRequest())
                .enqueue(new Callback<MovieResponse>() {

                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            if(response.isSuccessful()){
                                BusProvider.getInstance().post(new FetchMovieDetailsEvent.OnLoaded(response.body()));
                            }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        BusProvider.getInstance().post(new FetchMovieDetailsEvent.OnLoadingError(t.getMessage(),-1));
                    }
                });
    }



    public class MyResponse  implements Callback<ApiResponse>{

        @Override
        public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
            if(response.isSuccessful()){
                mBus.post(new FetchMovieListEvent.OnLoaded(response.body()));
            }
            else{
                Log.d("P1", "Errorneous response" + response.message());
            }
        }

        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            mBus.post(new FetchMovieListEvent.OnLoadingError(t.getMessage(),-1));
        }
    }


}
