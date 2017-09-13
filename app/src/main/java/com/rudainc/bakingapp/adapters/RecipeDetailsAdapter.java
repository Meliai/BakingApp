package com.rudainc.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.activities.RecipeDetailsActivity;
import com.rudainc.bakingapp.models.Ingredient;
import com.rudainc.bakingapp.models.Step;
import com.rudainc.bakingapp.utils.BakingKeys;

import java.util.ArrayList;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.IngredientsViewHolder> implements BakingKeys {

    private ArrayList<Step> steps = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    private Context context;

    private OnStepsListener onStepsListener;

    public RecipeDetailsAdapter(Context context, OnStepsListener onStepsListener) {
        this.context = context;
        this.onStepsListener = onStepsListener;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public final TextView mRecipeIngrediens;

        public IngredientsViewHolder(View view) {
            super(view);
            mRecipeIngrediens = (TextView) view.findViewById(R.id.tv_ingredients);

        }
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_ingredients;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeDetailsAdapter.IngredientsViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_INGREDIENTS) {
            String text = "";
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = ingredients.get(i);
                text = ingredient.getIngredient() + " - " + ingredient.getQuantity() + " " + ingredient.getMeasure() + "\n";
            }
            holder.mRecipeIngrediens.setText(text);
        }
//        if (holder.getItemViewType() == ITEM_STEPS) {
//            ItemKeywordHolder itemKeywordHolder = (ItemKeywordHolder) holder;
//            Keyword keyword = (Keyword) list.get(holder.getAdapterPosition());
//            itemKeywordHolder.bind(keyword, context);
//            itemKeywordHolder.getMainView().setOnClickListener(view -> {
//                if(isSelectMode) {
//                    if (onAdapterKeywordsListener != null) {
//                        if(keyword.isSelected())
//                            onAdapterKeywordsListener.onItemUnselected(keyword);
//                        else
//                            onAdapterKeywordsListener.onItemSelected(keyword);
//                    }
//                }
//            });
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_INGREDIENTS;
        } else {
            return ITEM_STEPS;
        }

    }


    public void updateList(ArrayList<Ingredient> ingredientArrayList, ArrayList<Step> stepArrayList) {
        this.ingredients.clear();
        this.steps.clear();

        ingredients = ingredientArrayList;
        steps = stepArrayList;

        notifyDataSetChanged();
    }

    public void setOnStepsListener(OnStepsListener onStepsListener) {
        this.onStepsListener = onStepsListener;
    }


    @Override
    public long getItemId(int position) {
        return steps.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        if (null == ingredients) return 0;
        return ingredients.size();
    }


    public interface OnStepsListener {
        void onItemClick(Step step);
    }
}
