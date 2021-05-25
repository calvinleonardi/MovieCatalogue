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

import com.example.moviecataloguesubmissionfinal.R;
import com.example.moviecataloguesubmissionfinal.Adapter.FilmFavoriteAdapter;
import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.FilmViewModel;
import com.example.moviecataloguesubmissionfinal.db.FilmDAO;
import com.example.moviecataloguesubmissionfinal.db.FilmDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavTvshow extends Fragment {

    private FilmFavoriteAdapter adapter;
    @BindView(R.id.rcv_fav_tvshow)
    RecyclerView rcv_fav_tvshow;

    public FragmentFavTvshow() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_tvshow, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcv_fav_tvshow.setHasFixedSize(true);
        rcv_fav_tvshow.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FilmFavoriteAdapter(getActivity());
        rcv_fav_tvshow.setAdapter(adapter);

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
        return movieDAO.getFilmsByFilmType("tv");
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
        List<Film> films = filmDAO.getAllFavMoviesByType("tv");
        adapter.setDataFilm(films);
    }

}
