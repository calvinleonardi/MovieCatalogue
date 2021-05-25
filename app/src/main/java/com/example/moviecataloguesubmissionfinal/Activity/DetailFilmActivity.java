package com.example.moviecataloguesubmissionfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.R;
import com.example.moviecataloguesubmissionfinal.db.FilmDAO;
import com.example.moviecataloguesubmissionfinal.db.FilmDB;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFilmActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_detail)
    Toolbar toolbar;
    @BindView(R.id.txt_name)
    TextView title;
    @BindView(R.id.txt_description)
    TextView description;
    @BindView(R.id.languange)
    TextView originalLanguage;
    @BindView(R.id.txt_daterelease)
    TextView releaseDate;
    @BindView(R.id.txt_voteavg)
    TextView voteAverage;
    @BindView(R.id.img_photo)
    ImageView poster;
    @BindView(R.id.pgDetailMovie)
    ProgressBar progressBar;

    private Film film;
    private FilmDAO filmDAO;
    private Menu menu;
    private boolean isFav;
    ArrayList<Film> dbMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        film = intent.getParcelableExtra("film");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.details);

        showDetail(film);
        filmDAO = Room.databaseBuilder(this, FilmDB.class, "db_movie")
                .allowMainThreadQueries()
                .build()
                .getFilmDAO();

        isFav = false;
        checkFav();
    }

    private void checkFav() {
        dbMovies = (ArrayList<Film>) filmDAO.getAllFavMovies();
        for (Film film: dbMovies){
            if (this.film.getId() == film.getId()){
                isFav = true;
            }
            if (isFav) {
                break;
            }
        }
    }

    private void showDetail(Film film) {
        String url_image = "https://image.tmdb.org/t/p/w185/" + film.getPhoto();

        title.setText(film.getTitle());
        originalLanguage.setText(film.getOriginal_language());
        description.setText(film.getOverview());
        releaseDate.setText(film.getRelease_date());
        voteAverage.setText(film.getVote_average());
        Glide.with(DetailFilmActivity.this)
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(poster);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        this.menu = menu;

        setFav();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        else if (item.getItemId() == R.id.menu_item_add_favorite_menu_detail){
            if (!isFav){
                filmDAO.insert(film);
                setResult(RESULT_OK);
                Intent brIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                sendBroadcast(brIntent);
                isFav = true;
                setFav();
            }
            else{
                FilmDAO filmDAO = Room.databaseBuilder(this, FilmDB.class, "db_movie")
                        .allowMainThreadQueries()
                        .build()
                        .getFilmDAO();
                filmDAO.deleteByUid(film.getId());
                dbMovies.remove(film);
                isFav = false;
                setFav();
                Intent brIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                this.sendBroadcast(brIntent);
            }
        }
        return true;
    }

    private void setFav(){
        if (isFav) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        } else {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
    }
}
