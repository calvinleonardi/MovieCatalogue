package com.example.moviecataloguesubmissionfinal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviecataloguesubmissionfinal.Activity.DetailFilmActivity;
import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder>{

    private Context context;
    private ArrayList<Film> dataFilm = new ArrayList<>();

    public FilmAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(ArrayList<Film> movies) {
        this.dataFilm = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.bind(dataFilm.get(position));
    }

    @Override
    public int getItemCount() {
        return dataFilm.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder{

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

        public FilmViewHolder(@NonNull View itemView) {
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
            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(poster);
        }
    }
}
