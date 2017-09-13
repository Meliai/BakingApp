package com.rudainc.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.adapters.RecipesAdapter;
import com.rudainc.bakingapp.async.GetRecipes;
import com.rudainc.bakingapp.async.OnGetDataCompleted;
import com.rudainc.bakingapp.models.BakingSample;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RecipesAdapter.RecipesAdapterOnClickHandler, OnGetDataCompleted {

    @BindView(R.id.rv)
    RecyclerView rvRecipes;

    @BindView(R.id.no_data)
    LinearLayout llNoData;

    private GetRecipes getRecipes;
    private RecipesAdapter recipesAdapter;
    private OnGetDataCompleted onGetDataCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        recipesAdapter = new RecipesAdapter(this, this);
        rvRecipes.setAdapter(recipesAdapter);


        if (isOnline(this)) {
            getRecipes = new GetRecipes(this, this);
            getRecipes.execute();
        } else{
            setNoUI();
            showSnackBar(getString(R.string.smth_went_wrong), true);
        }
    }

    @Override
    public void onClick(BakingSample bakingSample) {
        Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_DATA, bakingSample);
        startActivity(intent);
    }

    @Override
    public void onGetDataCompleted(final ArrayList<BakingSample> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (data != null) {
                    if (data.isEmpty())
                        setNoUI();
                    recipesAdapter.setData(data);
                } else {
                    showSnackBar(getString(R.string.smth_went_wrong), true);
                    setNoUI();
                }
            }
        });
    }

    private void setNoUI() {
        llNoData.setVisibility(View.VISIBLE);
        rvRecipes.setVisibility(View.GONE);
    }

    @Override
    public void onGetDataError(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showSnackBar(message, true);
                setNoUI();
            }
        });
    }
}
