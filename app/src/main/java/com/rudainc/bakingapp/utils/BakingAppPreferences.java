package com.rudainc.bakingapp.utils;

import android.content.Context;
import android.preference.PreferenceManager;


public class BakingAppPreferences {

    public static void setRecipeData(Context context, String recipe_name, String ingredients){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(BakingKeys.RECIPE_NAME, recipe_name).apply();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(BakingKeys.RECIPE_INGREDIENTS, ingredients).apply();
    }

    public static String getRecipeName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(BakingKeys.RECIPE_NAME, "");
    }

    public static String getRecipeIngredients(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(BakingKeys.RECIPE_INGREDIENTS, "");
    }
}
