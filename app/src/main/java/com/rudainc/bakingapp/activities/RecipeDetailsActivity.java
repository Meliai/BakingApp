package com.rudainc.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.adapters.RecipeDetailsAdapter;
import com.rudainc.bakingapp.adapters.RecipesAdapter;
import com.rudainc.bakingapp.models.BakingSample;
import com.rudainc.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends BaseActivity implements RecipeDetailsAdapter.OnStepsListener {

    private BakingSample bakingSample;
    private RecipeDetailsAdapter recipeDetailsAdapter;

    @BindView(R.id.no_data)
    LinearLayout llNoData;

    @BindView(R.id.rv)
    RecyclerView rvSteps;

    @BindView(R.id.tv_ingredients)
    TextView mIngredients;

    private String ingredients_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        recipeDetailsAdapter = new RecipeDetailsAdapter(this, this);
        rvSteps.setAdapter(recipeDetailsAdapter);
        rvSteps.setFocusable(false);

        bakingSample = (BakingSample) getIntent().getParcelableExtra(RECIPE_DATA);
        if (bakingSample != null) {
            getSupportActionBar().setTitle(bakingSample.getName());
            recipeDetailsAdapter.updateList(bakingSample.getSteps());
            for (int i = 0; i < bakingSample.getIngredients().size(); i++) {
                ingredients_text = ingredients_text + bakingSample.getIngredients().get(i).getIngredient() + " - " +
                        bakingSample.getIngredients().get(i).getQuantity() + " " + bakingSample.getIngredients().get(i).getMeasure() + "\n";
            }
            mIngredients.setText(ingredients_text.trim());
        } else
            setNoUI();


    }

    private void setNoUI() {
        llNoData.setVisibility(View.VISIBLE);
        rvSteps.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Step step) {
        Intent intent = new Intent(RecipeDetailsActivity.this, StepsActivity.class);
        intent.putExtra(RECIPE_NAME, bakingSample.getName());
        intent.putExtra(STEPS, step);
        startActivity(intent);
    }
}
