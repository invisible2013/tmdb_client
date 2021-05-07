package com.flatrocktech.mymovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.flatrocktech.mymovies.service.ApiClient;
import com.flatrocktech.mymovies.service.models.Movie;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CustomViewHolder> {

    public static final String MOVIE_ID = "movieId";
    private List<Movie> dataList;
    private Context context;

    public MovieAdapter(List<Movie> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.view_movie_item, parent, false);
        return new CustomViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        Movie item = dataList.get(position);
        builder.build()
                .load(ApiClient.getImageFullPath(item.getPosterPath()))
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.moviePoster);

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra(MOVIE_ID, item.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        private final ImageView moviePoster;
        private CardView cardView;

        CustomViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            moviePoster = itemView.findViewById(R.id.movie_item_image);
        }
    }
}
