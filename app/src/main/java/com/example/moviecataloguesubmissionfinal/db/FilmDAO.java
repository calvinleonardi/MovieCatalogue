package com.example.moviecataloguesubmissionfinal.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviecataloguesubmissionfinal.Class.Film;

import java.util.List;

@Dao
public interface FilmDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Film... movies);

    @Query("SELECT * FROM film")
    Cursor selectAll();

    @Query("SELECT * FROM film where id = :uid")
    Cursor selectById(long uid);

    @Query("SELECT * FROM film")
    List<Film> getAllFavMovies();

    @Query("SELECT * FROM film where filmType = :type")
    List<Film> getAllFavMoviesByType(String type);

    @Query("DELETE FROM film WHERE id = :uid")
    void deleteByUid(int uid);

    @Query("SELECT COUNT(id) FROM film WHERE title = :title")
    int getMovieByTitle(String title);

    @Query("SELECT * FROM film WHERE filmType = :movieType")
    List<Film> getFilmsByFilmType(String movieType);

}
