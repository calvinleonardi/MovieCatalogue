package com.example.moviecataloguesubmissionfinal.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecataloguesubmissionfinal.Adapter.FilmAdapter;
import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.FilmViewModel;
import com.example.moviecataloguesubmissionfinal.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMovie extends Fragment {

    private FilmAdapter adapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rcv_movie)
    RecyclerView rcvFilmMovie;

    public FragmentMovie() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FilmAdapter(getActivity());
        rcvFilmMovie.setHasFixedSize(true);
        rcvFilmMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvFilmMovie.setAdapter(adapter);

        FilmViewModel movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmViewModel.class);
        movieViewModel.setFilm("movie");
        movieViewModel.getFilms().observe(getViewLifecycleOwner(), getMovie);
        showLoading(true);
        setRetainInstance(true);
    }

    private Observer<ArrayList<Film>> getMovie = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(ArrayList<Film> movies) {
            if (movies != null) {
                adapter.setMovies(movies);
            }
            showLoading(false);

        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}

