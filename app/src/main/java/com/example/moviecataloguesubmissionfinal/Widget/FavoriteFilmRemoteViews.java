package com.example.moviecataloguesubmissionfinal.Widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.R;
import com.example.moviecataloguesubmissionfinal.db.FilmDAO;
import com.example.moviecataloguesubmissionfinal.db.FilmDB;

import java.util.ArrayList;

public class FavoriteFilmRemoteViews implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Film> items = new ArrayList<>();
    private FilmDB filmDB;
    private Context context;

    public FavoriteFilmRemoteViews(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        final long token = Binder.clearCallingIdentity();
        filmDB = Room.databaseBuilder(context.getApplicationContext(), FilmDB.class, "db_movie")
                .allowMainThreadQueries()
                .build();
        Binder.restoreCallingIdentity(token);
    }

    @Override
    public void onDataSetChanged() {
        try {
            FilmDAO filmDAO = filmDB.getFilmDAO();
            items.clear();
            items.addAll(filmDAO.getAllFavMovies());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        filmDB.close();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w185" + items.get(i).getPhoto())
                    .apply(new RequestOptions().fitCenter())
                    .submit(700, 450)
                    .get();
            remoteViews.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoriteFilmWidget.ITEM_EXTRA, i);
        Intent intent = new Intent();
        intent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
