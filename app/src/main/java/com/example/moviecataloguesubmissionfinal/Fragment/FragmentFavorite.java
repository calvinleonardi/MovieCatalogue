package com.example.moviecataloguesubmissionfinal.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.moviecataloguesubmissionfinal.Adapter.ViewPagerAdapter;
import com.example.moviecataloguesubmissionfinal.R;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavorite extends Fragment {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    public FragmentFavorite() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentFavMovie(), getString(R.string.tab_movie));
        adapter.addFragment(new FragmentFavTvshow(), getString(R.string.tab_tvshow));
        viewPager.setAdapter(adapter);
        // setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        setRetainInstance(true);
    }



    /*private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentFavMovie(), getResources().getString(R.string.tab_movie));
        adapter.addFragment(new FragmentFavTvshow(), getResources().getString(R.string.tab_tvshow));
        viewPager.setAdapter(adapter);
    }*/
}
