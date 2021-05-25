package com.example.filmfavapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.list_fav_film)
    RecyclerView rcv_film;

    private FilmAdapter adapter;
    public static int FILM_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        adapter = new FilmAdapter(getApplicationContext());
        rcv_film.setHasFixedSize(true);
        rcv_film.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv_film.setAdapter(adapter);

        getSupportLoaderManager().initLoader(FILM_CODE, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == FILM_CODE) {
            return new CursorLoader(getApplicationContext(), Components.CONTENT_URI, null, null, null, null);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == FILM_CODE){
            try {
                adapter.setCursor(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == FILM_CODE){
            adapter.setCursor(null);
        }
    }
}
