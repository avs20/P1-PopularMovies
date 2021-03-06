package com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus;

import com.siolabs.ashutoshsingh.p1_popularmovies.AppConstans;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.ApiResponse;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.Movie;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ashutoshsingh on 16-03-2016.
 */
public interface MovieService {

    @GET("popular?api_key="+ AppConstans.API_KEY)
    Call<ApiResponse> getPopularMovies(@Query("page") int pageNo);

    @GET("top_rated?api_key="+ AppConstans.API_KEY)
    Call<ApiResponse> getTopRatedMovies(@Query("page") int pageNo);

    @GET("{movieId}?api_key="+ AppConstans.API_KEY)
    Call<MovieResponse> getMovieDetails(@Path("movieId") String movieId);




}
