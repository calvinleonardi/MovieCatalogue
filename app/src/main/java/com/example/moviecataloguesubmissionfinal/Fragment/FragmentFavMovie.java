package com.example.moviecataloguesubmissionfinal.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.moviecataloguesubmissionfinal.Adapter.FilmFavoriteAdapter;
import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.FilmViewModel;
import com.example.moviecataloguesubmissionfinal.R;
import com.example.moviecataloguesubmissionfinal.db.FilmDAO;
import com.example.moviecataloguesubmissionfinal.db.FilmDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavMovie extends Fragment {

    @BindView(R.id.rcv_fav_movie)
    RecyclerView rcv_fav_movie;

    private FilmFavoriteAdapter adapter;

    public FragmentFavMovie() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcv_fav_movie.setHasFixedSize(true);
        rcv_fav_movie.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FilmFavoriteAdapter(getActivity());
        rcv_fav_movie.setAdapter(adapter);

        ArrayList<Film> dataFilm = (ArrayList<Film>) loadFavFilms();
        FilmViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmViewModel.class);
        viewModel.setFavFilm(dataFilm);
        viewModel.getFilms().observe(getViewLifecycleOwner(), getFilms);

        setRetainInstance(true);
    }

    private List<Film> loadFavFilms() {
        FilmDB database = Room.databaseBuilder(getActivity(), FilmDB.class, "db_movie")
                .allowMainThreadQueries()
                .build();
        FilmDAO movieDAO = database.getFilmDAO();
        return movieDAO.getFilmsByFilmType("movie");
    }

    private Observer<ArrayList<Film>> getFilms = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Film> films) {
            if (films != null) {
                adapter.setDataFilm(films);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        FilmDAO filmDAO = Room.databaseBuilder(getActivity(), FilmDB.class, "db_movie")
                .allowMainThreadQueries()
                .build()
                .getFilmDAO();
        List<Film> films = filmDAO.getAllFavMoviesByType("movie");
        adapter.setDataFilm(films);
    }
}
