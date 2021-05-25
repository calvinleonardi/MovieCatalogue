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

import com.example.moviecataloguesubmissionfinal.Adapter.FilmAdapter;
import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.FilmViewModel;
import com.example.moviecataloguesubmissionfinal.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSearch extends Fragment {

    @BindView(R.id.list_film_result)
    RecyclerView rcv_search;
    private FilmAdapter adapter;

    public FragmentSearch() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcv_search.setHasFixedSize(true);
        rcv_search.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FilmAdapter(getActivity());
        rcv_search.setAdapter(adapter);

        String query = getArguments().getString("query");

        FilmViewModel filmViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmViewModel.class);
        filmViewModel.searchFilm(query);
        filmViewModel.getFilms().observe(getViewLifecycleOwner(), getMovie);
        setRetainInstance(true);

    }

    private Observer<ArrayList<Film>> getMovie = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(ArrayList<Film> films) {
            if (films != null && films.size() > 0) {
                adapter.setMovies(films);
            }
        }
    };
}
