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

import com.example.moviecataloguesubmissionfinal.Class.Film;
import com.example.moviecataloguesubmissionfinal.FilmViewModel;
import com.example.moviecataloguesubmissionfinal.R;
import com.example.moviecataloguesubmissionfinal.Adapter.FilmAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTvshow extends Fragment {

    private FilmAdapter adapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rcv_tvshow)
    RecyclerView rcvTvshow;

    public FragmentTvshow() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FilmAdapter(getActivity());
        rcvTvshow.setHasFixedSize(true);
        rcvTvshow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvTvshow.setAdapter(adapter);

        FilmViewModel tvshowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmViewModel.class);
        tvshowViewModel.setFilm("tv");
        tvshowViewModel.getFilms().observe(getViewLifecycleOwner(), getTvshow);
        showLoading(true);
        setRetainInstance(true);
    }

    private Observer<ArrayList<Film>> getTvshow = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(ArrayList<Film> tvShows) {
            if (tvShows != null) {
                adapter.setMovies(tvShows);
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
