package com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.events;

import com.siolabs.ashutoshsingh.p1_popularmovies.models.ApiResponse;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.Movie;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.MovieResponse;

/**
 * Created by ashutoshsingh on 17-03-2016.
 */
public class FetchMovieDetailsEvent extends BaseCommEvent {
    public static final OnLoadingError FAILED = new OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnLoaded extends OnDone<MovieResponse>{
        public OnLoaded(MovieResponse movie) {
            super(movie);
        }
    }

    public static class OnLoadingStart extends OnStart<String> {
        public OnLoadingStart(String movieId) {
            super(movieId);
        }
    }

    public static class OnLoadingError extends OnFailed {
        public OnLoadingError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }
}
