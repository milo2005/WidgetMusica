package com.example.estacionvl_tc_014.widgetmusica.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.estacionvl_tc_014.widgetmusica.R;
import com.example.estacionvl_tc_014.widgetmusica.services.MusicService;

/**
 * Implementation of App Widget functionality.
 */
public class MusicWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.music_widget);
        //Proceso Play
        Intent play = new Intent(context, MusicService.class);
        play.setAction(MusicService.ACTION_PLAY);

        PendingIntent pendingPlay = PendingIntent.getService(context,101,play
                , PendingIntent.FLAG_UPDATE_CURRENT);

        //Proceso Pause
        Intent pause = new Intent(context, MusicService.class);
        pause.setAction(MusicService.ACTION_PAUSE);

        PendingIntent pendingPause = PendingIntent.getService(context,102,pause
                , PendingIntent.FLAG_UPDATE_CURRENT);

        //Proceso Stop
        Intent stop = new Intent(context, MusicService.class);
        stop.setAction(MusicService.ACTION_STOP);

        PendingIntent pendingStop = PendingIntent.getService(context,103,stop
                , PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.btn_play, pendingPlay);
        views.setOnClickPendingIntent(R.id.btn_pause, pendingPause);
        views.setOnClickPendingIntent(R.id.btn_stop, pendingStop);







        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

