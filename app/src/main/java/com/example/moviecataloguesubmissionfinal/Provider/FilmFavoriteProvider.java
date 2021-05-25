package com.example.moviecataloguesubmissionfinal.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.moviecataloguesubmissionfinal.db.FilmDAO;
import com.example.moviecataloguesubmissionfinal.db.FilmDB;

public class FilmFavoriteProvider extends ContentProvider {

    private FilmDAO filmDAO;
    private static final String DBNAME = "db_movie";
    private static final String DB_TABLE = "film";
    private static final String AUTHORITY = "com.example.moviecataloguesubmissionfinal.Provider";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CODE_FAV_DIR = 1;
    private static final int CODE_FAV_ITEM = 2;

    static {
        uriMatcher.addURI(AUTHORITY, DB_TABLE, CODE_FAV_DIR);
        uriMatcher.addURI(AUTHORITY, DB_TABLE + "/#", CODE_FAV_ITEM);
    }

    public FilmFavoriteProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        FilmDB filmDB = Room.databaseBuilder(getContext(), FilmDB.class, DBNAME).build();
        filmDAO = filmDB.getFilmDAO();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int code = uriMatcher.match(uri);
        Log.d("Code : ", String.valueOf(code));
        if (code == CODE_FAV_DIR || code == CODE_FAV_ITEM){
            final Context context = getContext();
            final Cursor cursor;
            if (context == null){
                return null;
            }
            if (code == CODE_FAV_DIR){
                cursor = filmDAO.selectAll();
            }else {
                cursor = filmDAO.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
