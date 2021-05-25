package com.example.moviecataloguesubmissionfinal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.moviecataloguesubmissionfinal.Fragment.FragmentFavMovie;
import com.example.moviecataloguesubmissionfinal.Fragment.FragmentFavTvshow;
import com.example.moviecataloguesubmissionfinal.Fragment.FragmentFavorite;
import com.example.moviecataloguesubmissionfinal.Fragment.FragmentMovie;
import com.example.moviecataloguesubmissionfinal.Fragment.FragmentSearch;
import com.example.moviecataloguesubmissionfinal.Fragment.FragmentTvshow;
import com.example.moviecataloguesubmissionfinal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.film_navigation)
    BottomNavigationView navigationView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        navigationView.setOnNavigationItemSelectedListener(bottom_navbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle(R.string.tab_movie);
        }
        if (savedInstanceState == null) {
            FragmentLoader(new FragmentMovie());
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottom_navbar = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            toolbar.collapseActionView();
            switch (menuItem.getItemId()) {
                case R.id.navigation_movie:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(R.string.tab_movie);
                    fragment = new FragmentMovie();
                    FragmentLoader(fragment);
                    return true;
                case R.id.navigation_tv_show:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(R.string.tab_tvshow);
                    fragment = new FragmentTvshow();
                    FragmentLoader(fragment);
                    return true;
                case R.id.navigation_favorite_movie:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(R.string.title_favorite_movie);
                    fragment = new FragmentFavMovie();
                    FragmentLoader(fragment);
                    return true;
                case R.id.navigation_favorite_tvshow:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(R.string.title_favorite_tv_show);
                    fragment = new FragmentFavTvshow();
                    FragmentLoader(fragment);
                    return true;
            }
            return false;
        }
    };

    private void FragmentLoader(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_main, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchViewText();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.language){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        else if (item.getItemId() == R.id.notification){
            Intent intent = new Intent(MainActivity.this, NotifSettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchViewText() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("query", s);
                    FragmentSearch fragment = new FragmentSearch();
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.container_main, fragment);
                    transaction.commit();
                    navigationView.getMenu().setGroupCheckable(0, false, true);
                } else {
                    navigationView.getMenu().setGroupCheckable(0, true, true);
                    navigationView.getMenu().getItem(0).setChecked(true);
                    FragmentLoader(new FragmentMovie());
                    if (getSupportActionBar() != null) {
                        toolbar.setTitle(R.string.tab_movie);
                    }
                }
                return false;
            }
        });
    }
}
