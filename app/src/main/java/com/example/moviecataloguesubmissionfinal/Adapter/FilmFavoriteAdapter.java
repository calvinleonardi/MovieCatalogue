package com.example.moviecataloguesubmissionfinal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviecataloguesubmissionfinal.Activity.DetailFilmActivity;
import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmFavoriteAdapter extends RecyclerView.Adapter<FilmFavoriteAdapter.FavFilmViewHolder> {

    private Context context;
    private List<Film> dataFilm = new ArrayList<>();

    public FilmFavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setDataFilm(List<Film> dataFilm) {
        this.dataFilm = dataFilm;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_fav, parent, false);
        return new FilmFavoriteAdapter.FavFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavFilmViewHolder holder, int position) {
        holder.bind(dataFilm.get(position));
    }

    @Override
    public int getItemCount() {
        return dataFilm.size();
    }

    public class FavFilmViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_name)
        TextView title;
        @BindView(R.id.txt_description)
        TextView desc;
        @BindView(R.id.movie_voteavg)
        TextView voteavg;
        @BindView(R.id.movie_votecount)
        TextView votecount;
        @BindView(R.id.img_photo)
        ImageView poster;

        public FavFilmViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent moveToDetailTvshow = new Intent(context, DetailFilmActivity.class);
                    moveToDetailTvshow.putExtra("film", dataFilm.get(getAdapterPosition()));
                    context.startActivity(moveToDetailTvshow);
                }
            });
        }

        void bind(Film film) {

            String url_image = "https://image.tmdb.org/t/p/w185" + film.getPhoto();

            title.setText(film.getTitle());
            desc.setText(film.getRelease_date());
            voteavg.setText(film.getVote_average());
            votecount.setText(film.getVote_count());
            Glide.with(context)
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(poster);
        }
    }
}
