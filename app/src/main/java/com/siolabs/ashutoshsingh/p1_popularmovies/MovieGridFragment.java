package com.siolabs.ashutoshsingh.p1_popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.BusProvider;
import com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus.events.FetchMovieListEvent;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.ListParams;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.Movie;
import com.siolabs.ashutoshsingh.p1_popularmovies.models.MovieResponse;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieGridFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieGridFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private GridLayoutManager mLayoutManager;
    private RecyclerView mMovieGridRecyclerView;
    private List<MovieResponse> movieList;
    private MovieAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public MovieGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieGridFragment newInstance(String param1, String param2) {
        MovieGridFragment fragment = new MovieGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        mMovieGridRecyclerView = (RecyclerView) view.findViewById(R.id.movie_grid_recycler_view);
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        mMovieGridRecyclerView.setLayoutManager(mLayoutManager);;

        //movieList = getResults();
        movieList = new ArrayList<MovieResponse>();
        adapter = new MovieAdapter(movieList);
        mMovieGridRecyclerView.setAdapter(adapter);
        //mMovieGridRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        ListParams params = new ListParams(1, "POPULAR");

        BusProvider.getInstance().post(new FetchMovieListEvent.OnLoadingStart(params));


        mMovieGridRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;

                            Log.v("...", "Last Item Wow !");
                            ListParams params = new ListParams(1+totalItemCount/20,"POPULAR");
                            BusProvider.getInstance().post(new FetchMovieListEvent
                                    .OnLoadingStart(params));
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(MovieResponse m) {
        if (mListener != null) {
            mListener.onFragmentInteraction(m);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(MovieResponse m);
    }

    public class MovieAdapter extends
            RecyclerView.Adapter<MovieAdapter.ViewHolder>{


        private List<MovieResponse> movieList;

        public MovieAdapter(List<MovieResponse> mList){
            movieList = mList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView moviePoster;

            public ViewHolder(View itemView) {
                super(itemView);
                moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);

                moviePoster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getLayoutPosition();
                        Log.d("SINGLE", "touched : "+position);

                        onButtonPressed(movieList.get(position));

                    }
                });

            }

        }

        @Override
        public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View movieView = inflater.inflate(R.layout.single_grid_item, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(movieView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
            // Get the data model based on position
            MovieResponse movie = movieList.get(position);

            // Set item views based on the data model
            ImageView poster = holder.moviePoster;


            Picasso.with(getActivity())
                    .load("http://image.tmdb.org/t/p/w185"+movie.getPoster_path())
                    .into(poster);


        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }
    }


    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }


    @Subscribe
    public void onMovieListLoaded(FetchMovieListEvent.OnLoaded onLoaded){

        loading = true;
        if(onLoaded.getResponse().getId() ==1)
            movieList.clear();

        movieList.addAll(onLoaded.getResponse().getResults());
        Log.d("GOT LIST",""+movieList.size());
        adapter.notifyDataSetChanged();
        Log.d("Sisey hi", adapter.getItemCount()+"");
    }

    @Subscribe
    public void onErrorResponse(FetchMovieListEvent.OnLoadingError onError){
        Log.d("GRID", onError.getErrorMessage());
    }

}
