package com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.events;

import com.siolabs.ashutoshsingh.p1_popularmovies.models.ApiResponse;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.ListParams;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.Movie;

import java.util.List;

/**
 * Created by ashutoshsingh on 16-03-2016.
 */
public class FetchMovieListEvent extends BaseCommEvent {

    public static final OnLoadingError FAILED = new OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnLoaded extends OnDone<ApiResponse>{
        public OnLoaded(ApiResponse response) {
            super(response);
        }
    }

    public static class OnLoadingStart extends OnStart<ListParams> {
        public OnLoadingStart(ListParams p) {
            super(p);
        }
    }

    public static class OnLoadingError extends OnFailed {
        public OnLoadingError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }

}
