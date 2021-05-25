package com.example.filmfavapp;

import android.net.Uri;

public class Components {
    public static final String TABLE_NAME = "film";
    public static final String PROVIDER = "com.example.moviecataloguesubmissionfinal.Provider";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(PROVIDER)
            .appendPath(TABLE_NAME)
            .build();
    public static final String TITLE_COLUMN = "title";
    public static final String VOTE_COUNT = "vote_count";
    public static final String VOTE_AVG = "vote_average";
    public static final String DESCRIPTION_COLUMN = "overview";
    public static final String POSTER_COLUMN = "photo";
    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
}
