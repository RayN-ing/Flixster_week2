package com.example.flixster2_week2.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster2_week2.DetailActivity;
import com.example.flixster2_week2.R;
import com.example.flixster2_week2.models.Movie;

import org.parceler.Parcels;

import java.util.List;


import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
{
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //Usually involves inflatinga a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreatreViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //Involves populating dara into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        //Bind the movie data into the VH
        holder.bind(movie);
    }

    //returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTtile;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTtile = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            tvTtile.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imgaeUrl = back drop Image
                imageUrl = movie.getBackdropPath();
            } else {
                // then imgaeUrl = poster Image
                imageUrl = movie.getPosterPath();
            }

            //1. Register click listener on the whole row


            int radius = 70; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

            Glide.with(context).load(imageUrl).into(ivPoster);

            //cant get rid of extra space on top/bottom either way
            //Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);
            //Glide.with(context).load(imageUrl).apply(RequestOptions.bitmapTransform(new RoundedCorners(140))).into(ivPoster);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //2. Naviaget to a new activity
                    //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);

                    //i.putExtra("title", movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
