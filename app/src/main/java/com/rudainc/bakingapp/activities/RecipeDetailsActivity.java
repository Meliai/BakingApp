package com.rudainc.bakingapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.adapters.RecipeDetailsAdapter;
import com.rudainc.bakingapp.adapters.RecipesAdapter;
import com.rudainc.bakingapp.models.BakingSample;
import com.rudainc.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends BaseActivity implements RecipeDetailsAdapter.OnStepsListener{

    private BakingSample bakingSample;
    private RecipeDetailsAdapter recipeDetailsAdapter;

    @BindView(R.id.no_data)
    LinearLayout llNoData;

    @BindView(R.id.rv)
    RecyclerView rvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        recipeDetailsAdapter = new RecipeDetailsAdapter(this, this);
        rvSteps.setAdapter(recipeDetailsAdapter);

        bakingSample = (BakingSample) getIntent().getParcelableExtra(RECIPE_DATA);
        if (bakingSample != null) {
            getSupportActionBar().setTitle(bakingSample.getName());
            recipeDetailsAdapter.updateList(bakingSample.getIngredients(), bakingSample.getSteps());
        } else
            setNoUI();


    }

    private void setNoUI() {
        llNoData.setVisibility(View.VISIBLE);
        rvSteps.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Step step) {

    }
}
