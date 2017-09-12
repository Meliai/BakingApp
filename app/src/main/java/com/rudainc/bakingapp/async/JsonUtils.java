package com.rudainc.bakingapp.async;

import com.rudainc.bakingapp.models.BakingSample;
import com.rudainc.bakingapp.models.Ingredient;
import com.rudainc.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<BakingSample> getDataFromJson(String jsonStr)
            throws JSONException {


        final String OWM_LIST = "results";
        final String OWM_MESSAGE_CODE = "code";


        ArrayList<BakingSample> parsedData = null;

        JSONObject recipesJson = new JSONObject(jsonStr);

        /* Is there an error? */
        if (recipesJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = recipesJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray jsonArray = recipesJson.getJSONArray(OWM_LIST);

        parsedData = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            int id;
            String name;
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            ArrayList<Step> steps = new ArrayList<>();
            String servings;
            String image;

            JSONObject objectRecipe = jsonArray.getJSONObject(i);

            id = objectRecipe.getInt("id");
            name = objectRecipe.getString("name");
            servings = objectRecipe.getString("servings");
            image = objectRecipe.getString("image");

            JSONArray arrayIngredients = objectRecipe.getJSONArray("ingredients");
            for (int count_ingredients = 0; count_ingredients < arrayIngredients.length(); count_ingredients++) {
                ingredients.add(i, new Ingredient(
                        objectRecipe.getInt("quantity"),
                        objectRecipe.getString("measure"),
                        objectRecipe.getString("ingredient")));
            }

            JSONArray arraySteps = objectRecipe.getJSONArray("steps");
            for (int count_steps = 0; count_steps < arraySteps.length(); count_steps++) {
                steps.add(i, new Step(
                        objectRecipe.getInt("id"),
                        objectRecipe.getString("shortDescription"),
                        objectRecipe.getString("description"),
                        objectRecipe.getString("videoURL"),
                        objectRecipe.getString("thumbnailURL")));
            }


            parsedData.add(i, new BakingSample(id, name, ingredients, steps, servings, image));
        }

        return parsedData;
    }

}
