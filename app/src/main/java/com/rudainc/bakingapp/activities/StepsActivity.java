package com.rudainc.bakingapp.activities;

import android.os.Bundle;

import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.models.Step;

import butterknife.ButterKnife;

public class StepsActivity extends BaseActivity{


    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);

        step = (Step) getIntent().getParcelableExtra(STEPS);
        if (step != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra(RECIPE_NAME));
        }


    }
}
