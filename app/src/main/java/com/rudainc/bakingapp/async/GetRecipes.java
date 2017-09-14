package com.rudainc.bakingapp.async;

import android.net.Uri;
import android.os.AsyncTask;

import com.rudainc.bakingapp.models.BakingSample;

import java.net.URL;
import java.util.ArrayList;

public class GetRecipes extends AsyncTask<Void, Void, ArrayList<BakingSample>> {

    private OnGetDataCompleted listener;

    public GetRecipes(OnGetDataCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<BakingSample> doInBackground(Void... params) {
        try {
            String jsonResponse = NetworkUtils
                    .getResponseFromHttpUrl(new URL(Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json").toString()));

            return JsonUtils.getDataFromJson(jsonResponse);

        } catch (Exception e) {
            listener.onGetDataError(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(ArrayList<BakingSample> bakingSamples) {
        listener.onGetDataCompleted(bakingSamples);
    }
}