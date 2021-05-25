package com.example.moviecataloguesubmissionfinal.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.moviecataloguesubmissionfinal.Class.Film;

@Database(entities = {Film.class}, version = 1)
public abstract class FilmDB extends RoomDatabase {
    public abstract FilmDAO getFilmDAO();
}
