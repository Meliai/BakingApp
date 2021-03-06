package com.rudainc.bakingapp.async;

import com.rudainc.bakingapp.models.BakingSample;
import com.rudainc.bakingapp.models.Ingredient;
import com.rudainc.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class JsonUtils {

    public static ArrayList<BakingSample> getDataFromJson(String jsonStr)
            throws JSONException {


        ArrayList<BakingSample> parsedData = null;
        ArrayList<Ingredient> ingredients;
        ArrayList<Step> steps;

        JSONArray recipesJson = new JSONArray(jsonStr);

        parsedData = new ArrayList<>();


        for (int i = 0; i < recipesJson.length(); i++) {
            int id;
            String name;
            ingredients = new ArrayList<>();
            steps = new ArrayList<>();
            String servings;
            String image;

            JSONObject objectRecipe = recipesJson.getJSONObject(i);

            id = objectRecipe.getInt("id");
            name = objectRecipe.getString("name");
            servings = objectRecipe.getString("servings");
            image = objectRecipe.getString("image");

            JSONArray arrayIngredients = objectRecipe.getJSONArray("ingredients");
            for (int count_ingredients = 0; count_ingredients < arrayIngredients.length(); count_ingredients++) {
                JSONObject objectIngredients = arrayIngredients.getJSONObject(count_ingredients);
                ingredients.add(new Ingredient(
                        objectIngredients.getString("quantity"),
                        objectIngredients.getString("measure"),
                        objectIngredients.getString("ingredient")));
            }

            JSONArray arraySteps = objectRecipe.getJSONArray("steps");
            for (int count_steps = 0; count_steps < arraySteps.length(); count_steps++) {
                JSONObject objectSteps = arraySteps.getJSONObject(count_steps);
                steps.add(new Step(
                        objectSteps.getInt("id"),
                        objectSteps.getString("shortDescription"),
                        objectSteps.getString("description"),
                        objectSteps.getString("videoURL"),
                        objectSteps.getString("thumbnailURL")));

            }


            parsedData.add(i,new BakingSample(id, name, ingredients, steps, servings, image));

        }

        return parsedData;
    }

}
