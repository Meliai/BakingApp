package com.rudainc.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.adapters.RecipeDetailsAdapter;
import com.rudainc.bakingapp.fragments.StepDetailFragment;
import com.rudainc.bakingapp.models.BakingSample;
import com.rudainc.bakingapp.models.Step;
import com.rudainc.bakingapp.utils.BakingAppPreferences;
import com.rudainc.bakingapp.widget.WidgetService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeDetailsActivity extends BaseActivity implements RecipeDetailsAdapter.OnStepsListener {

    private static final String SCROLL_POSITION = "scroll_position";
    private BakingSample bakingSample;

    @BindView(R.id.no_data)
    LinearLayout llNoData;

    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;

    @BindView(R.id.tv_ingredients)
    TextView mIngredients;

    private boolean mTwoPane;

    @OnClick(R.id.fab)
    void updateWidget(){
        BakingAppPreferences.setRecipeData(this, bakingSample.getName(),ingredients_text);
        WidgetService.startActionUpdateRecipeWidget(this);
    }

    private String ingredients_text = "";

    @BindView(R.id.root)
    NestedScrollView root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        RecipeDetailsAdapter recipeDetailsAdapter = new RecipeDetailsAdapter(this, this);
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

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be
            // in two-pane mode.
            mTwoPane = true;
            openDetails(bakingSample.getSteps().get(0));
        }

        if (savedInstanceState != null) {
            final int pos = savedInstanceState.getInt(SCROLL_POSITION);
            Log.i(SCROLL_POSITION, pos +"");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    root.smoothScrollTo(0, pos);
                }
            }, 200);
        }
    }

    private void setNoUI() {
        llNoData.setVisibility(View.VISIBLE);
        rvSteps.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Step step) {
        if (mTwoPane) {
           openDetails(step);
        } else {
            Intent intent = new Intent(RecipeDetailsActivity.this, StepsActivity.class);
            intent.putExtra(RECIPE_NAME, bakingSample.getName());
            intent.putExtra(STEPS, step);
            startActivity(intent);
        }

    }

    private void openDetails(Step step) {
        Bundle arguments = new Bundle();
        arguments.putString(RECIPE_NAME, bakingSample.getName());
        arguments.putParcelable(STEPS, step);
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_detail_container, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(SCROLL_POSITION, rvSteps.getY());
    }
}
