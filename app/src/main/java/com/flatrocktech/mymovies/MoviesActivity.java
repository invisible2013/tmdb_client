package com.flatrocktech.mymovies;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flatrocktech.mymovies.service.gateway.MovieProvider;
import com.flatrocktech.mymovies.service.gateway.methods.GetAllMoviesCall;
import com.flatrocktech.mymovies.service.gateway.methods.GetFavouriteMoviesCall;
import com.flatrocktech.mymovies.service.gateway.methods.GetPopularMoviesCall;
import com.flatrocktech.mymovies.service.gateway.methods.GetTopRatedMoviesCall;
import com.google.android.material.tabs.TabLayout;

public class MoviesActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MoviePagerAdapter mMoviePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movies);
        mTabLayout = findViewById(R.id.movies_tab);


        mViewPager = findViewById(R.id.movies_view_pager);
        mMoviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(mMoviePagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    class MoviePagerAdapter extends FragmentPagerAdapter {

        public MoviePagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            MoviesFragment moviesFragment = new MoviesFragment();
            moviesFragment.setMovieProvider(Tabs.values()[position].movieProvider);
            moviesFragment.setTabId(position);
            return moviesFragment;
        }

        @Override
        public int getCount() {
            return Tabs.values().length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(Tabs.values()[position].title);
        }
    }

    enum Tabs {
        ALL(R.string.all, new GetAllMoviesCall()),
        POPULAR(R.string.popular, new GetPopularMoviesCall()),
        TOP_RATED(R.string.top_rated, new GetTopRatedMoviesCall()),
        FAVORITE(R.string.favorite, new GetFavouriteMoviesCall());
        int title;
        MovieProvider movieProvider;

        Tabs(int title, MovieProvider movieProvider) {
            this.title = title;
            this.movieProvider = movieProvider;
        }
    }
}