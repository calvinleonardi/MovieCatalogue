package com.example.moviecataloguesubmissionfinal.Widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

public class FavoriteFilmService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteFilmRemoteViews(getApplicationContext());
    }
}
