package com.example.moviecataloguesubmissionfinal;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecataloguesubmissionfinal.Activity.MainActivity;
import com.example.moviecataloguesubmissionfinal.Api.ClientApi;
import com.example.moviecataloguesubmissionfinal.Api.EndpointApi;
import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.Class.FilmResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmViewModel extends ViewModel {
    private EndpointApi api = ClientApi.getClient().create(EndpointApi.class);
    private MutableLiveData<ArrayList<Film>> listFilm = new MutableLiveData<>();

    public void setFilm(final String filmType){
        Call<FilmResponse> call = api.getMovies(filmType);
        call.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                try {
                    ArrayList<Film> films = response.body().getResults();
                    for (Film data : films){
                        data.setFilmType(filmType);
                    }
                    listFilm.postValue(films);
                }catch (Exception e){
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());
            }
        });
    }

    public LiveData<ArrayList<Film>> getFilms() {
        return listFilm;
    }

    public void setFavFilm(ArrayList<Film> films) {
        listFilm.postValue(films);
    }

    public void searchFilm(String query) {
        Call<FilmResponse> call = api.searchMovies(query);
        call.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                try {
                    ArrayList<Film> films = response.body().getResults();
                    listFilm.postValue(films);
                }catch (Exception e){
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());
            }
        });
    }
}
