package com.siolabs.ashutoshsingh.p1_popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.BusProvider;
import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.events.FetchMovieDetailsEvent;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.Movie;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.MovieResponse;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    private MovieResponse movie;

    private TextView movieTitle;
    private TextView movieReleaseYear;
    private TextView movieLength;
    private TextView movieRating;
    private TextView movieOverview;
    private ImageView moviePoster;




    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(MovieResponse m) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,m);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(ARG_PARAM1);
            BusProvider.getInstance().post(new FetchMovieDetailsEvent.OnLoadingStart(""+movie.getId()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);


        movieTitle = (TextView) view.findViewById(R.id.detail_movie_title);
        movieReleaseYear = (TextView) view.findViewById(R.id.detail_movie_year);
        movieLength = (TextView) view.findViewById(R.id.detail_movie_length);
        movieRating = (TextView) view.findViewById(R.id.detail_movie_rating);
        moviePoster = (ImageView) view.findViewById(R.id.detail_movie_poster);
        movieOverview = (TextView) view.findViewById(R.id.detail_movie_overview);






        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }


    @Subscribe
    public void movileDetailsLoaded(FetchMovieDetailsEvent.OnLoaded onLoaded){
        movie = onLoaded.getResponse();
        fillData();
    }

    private void fillData() {
        movieTitle.setText(movie.getOriginal_title());
        movieOverview.setText(movie.getOverview());
        movieReleaseYear.setText(movie.getRelease_date().substring(0,4));
        movieRating.setText(movie.getVote_average()+"/10");
        movieLength.setText(movie.getRuntime()+" min");

        Picasso.with(getActivity())
                .load(movie.getPoster_path())
                .into(moviePoster);

    }

    @Subscribe
    public void failMovieDetail(FetchMovieDetailsEvent.OnLoadingError error){
        Log.d("MOVIE DETAILS", error.getErrorMessage());
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }
}
