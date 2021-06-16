package com.flatrocktech.mymovies;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flatrocktech.mymovies.service.gateway.Callback;
import com.flatrocktech.mymovies.service.gateway.MovieProvider;
import com.flatrocktech.mymovies.service.models.Movie;

import java.util.List;

public class MoviesFragment extends Fragment {

    private int tabId;
    private MovieAdapter adapter;
    private RecyclerView recyclerView;
    private TextView mEmptyTextView;
    private ProgressDialog progressDialog;
    public static boolean MANUAL_UPDATE = false;

    private MovieProvider movieProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        recyclerView = view.findViewById(R.id.movies_recycler_view);
        mEmptyTextView = view.findViewById(R.id.movies_empty_view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        loadMovies();
    }

    void loadMovies() {
        movieProvider.getMovies(new Callback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> movieList) {
                progressDialog.dismiss();
                if (movieList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                } else {
                    adapter = new MovieAdapter(movieList, getContext());
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    mEmptyTextView.setVisibility(View.GONE);
                }
                MANUAL_UPDATE = false;
            }

            @Override
            public void onFailure(int message) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MANUAL_UPDATE && getTabId() == 3) {
            loadMovies();
        }
    }

    public void setMovieProvider(MovieProvider movieProvider) {
        this.movieProvider = movieProvider;
    }
}
