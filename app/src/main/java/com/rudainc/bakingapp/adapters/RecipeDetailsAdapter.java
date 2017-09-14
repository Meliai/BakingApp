package com.rudainc.bakingapp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.models.Step;

import java.util.ArrayList;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.StepsViewHolder> {

    private ArrayList<Step> steps = new ArrayList<>();

    private Context context;

    private OnStepsListener onStepsListener;

    public RecipeDetailsAdapter(Context context, OnStepsListener onStepsListener) {
        this.context = context;
        this.onStepsListener = onStepsListener;
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mRecipeStep;

        public StepsViewHolder(View view) {
            super(view);
            mRecipeStep = (TextView) view.findViewById(R.id.tv_step);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step step = steps.get(adapterPosition);
            onStepsListener.onItemClick(step);

            ((CardView)view).setCardBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryLight));
        }
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_steps;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.mRecipeStep.setText(step.getShortDescription());
    }

    public void updateList(ArrayList<Step> stepArrayList) {
        this.steps.clear();
        steps = stepArrayList;
        notifyDataSetChanged();
    }

    public void setOnStepsListener(OnStepsListener onStepsListener) {
        this.onStepsListener = onStepsListener;
    }

    public interface OnStepsListener {
        void onItemClick(Step step);
    }


    @Override
    public long getItemId(int position) {
        return steps.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        if (null == steps) return 0;
        return steps.size();
    }



}
