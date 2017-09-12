package com.rudainc.bakingapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    private GetRecipes getRecipes;
    private RecipesAdapter recipesAdapter;

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
        } else
            showSnackBar(getString(R.string.smth_went_wrong), true);
    }

    @Override
    public void onClick(BakingSample bakingSample, ImageView view) {

    }

    @Override
    public void onGetDataCompleted(ArrayList<BakingSample> data) {
        if (data != null) {
            if (data.isEmpty())
                setNoReviewsUI(getResources().getString(R.string.no_data));
            recipesAdapter.setData(data);
        } else {
            showSnackBar(getString(R.string.smth_went_wrong), true);
            setNoReviewsUI(getResources().getString(R.string.cant_upload_data));
        }
    }

    private void setNoReviewsUI(String text) {
        tvNoData.setVisibility(View.VISIBLE);
        tvNoData.setText(text);
        rvRecipes.setVisibility(View.GONE);
    }

    @Override
    public void onGetDataError(String message) {
        showSnackBar(message, true);
        setNoReviewsUI(getResources().getString(R.string.cant_upload_data));
    }
}
