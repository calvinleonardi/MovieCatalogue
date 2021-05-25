package com.example.moviecataloguesubmissionfinal.Api;

import com.example.moviecataloguesubmissionfinal.Class.FilmResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndpointApi {
    @GET("discover/{type}")
    Call<FilmResponse> getMovies(@Path("type") String filmType);

    @GET("search/multi")
    Call<FilmResponse> searchMovies(@Query("query") String query);

    @GET("discover/movie")
    Call<FilmResponse> getReleasedMovies(@Query("primary_release_date.gte") String date, @Query("primary_release_date.lte") String today);
}
