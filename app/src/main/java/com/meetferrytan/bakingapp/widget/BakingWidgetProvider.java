package com.meetferrytan.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.meetferrytan.bakingapp.R;

/**
 * Created by ferrytan on 9/5/17.
 */

public class BakingWidgetProvider extends AppWidgetProvider {

    public static final String PREF_RECIPE_NAME = "RECIPE_NAME";
    public static final String PREF_RECIPE_ALL_INGREDIENTS = "ALL_INGREDIENTS";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeName= sharedPreferences.
                getString(PREF_RECIPE_NAME, context.getString(R.string.label_no_recipe_to_show));

        String formattedIngredients = sharedPreferences.
                getString(PREF_RECIPE_ALL_INGREDIENTS, context.getString(R.string.label_no_ingredients_to_show));

        views.setTextViewText(R.id.recipeName,recipeName);
        views.setTextViewText(R.id.recipeIngredients,formattedIngredients );

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context,appWidgetIds);
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

