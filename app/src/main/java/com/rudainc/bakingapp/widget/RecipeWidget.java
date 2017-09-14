package com.rudainc.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.activities.MainActivity;
import com.rudainc.bakingapp.utils.BakingAppPreferences;

public class RecipeWidget extends AppWidgetProvider {

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews rv = getIngredientsListRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.tv_ingredients);
    }

    private static RemoteViews getIngredientsListRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        Log.i("WIDGET", BakingAppPreferences.getRecipeName(context)+" "+ BakingAppPreferences.getRecipeIngredients(context));
        if (!BakingAppPreferences.getRecipeIngredients(context).isEmpty()) {
            views.setTextViewText(R.id.tv_title, BakingAppPreferences.getRecipeName(context));
            views.setTextViewText(R.id.tv_ingredients, BakingAppPreferences.getRecipeIngredients(context));
        }
        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.tv_ingredients, appPendingIntent);


        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetService.startActionUpdateRecipeWidget(context);
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


