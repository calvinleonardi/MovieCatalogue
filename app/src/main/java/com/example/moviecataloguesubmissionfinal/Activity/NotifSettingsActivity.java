package com.example.moviecataloguesubmissionfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.example.moviecataloguesubmissionfinal.NotifReceiver;
import com.example.moviecataloguesubmissionfinal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifSettingsActivity extends AppCompatActivity {

    @BindView(R.id.daily_switch)
    SwitchCompat dailySwitch;
    @BindView(R.id.release_switch)
    SwitchCompat releasedSwitch;
    @BindView(R.id.toolbar_notif)
    Toolbar toolbar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private NotifReceiver notifReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);

        ButterKnife.bind(this);
        sharedPreferences =getSharedPreferences("reminder", Context.MODE_PRIVATE);
        notifReceiver = new NotifReceiver(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.notification_setting_title);
        checkChangedSwitch();
        setPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

    private void checkChangedSwitch() {
        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                editor = sharedPreferences.edit();
                editor.putBoolean("daily_reminder", check);
                editor.apply();
                if (check){
                    notifReceiver.setDailyReminder();
                }else {
                    notifReceiver.cancelDailyReminder(getApplicationContext());
                }
            }
        });
        releasedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                editor = sharedPreferences.edit();
                editor.putBoolean("released_reminder", check);
                editor.apply();
                if (check){
                    notifReceiver.setReleasedTodayFilmReminder();
                }else {
                    notifReceiver.cancelReleaseTodayFilmReminder(getApplicationContext());
                }
            }
        });
    }

    private void setPreferences() {
        boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);
        boolean releaseReminder = sharedPreferences.getBoolean("released_reminder", false);

        dailySwitch.setChecked(dailyReminder);
        releasedSwitch.setChecked(releaseReminder);
    }
}
