package com.rudainc.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.models.BakingSample;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private final Context context;
    private ArrayList<BakingSample> mData = new ArrayList<>();


    private final RecipesAdapterOnClickHandler mClickHandler;

    public interface RecipesAdapterOnClickHandler {
        void onClick(BakingSample bakingSample);
    }


    public RecipesAdapter(Context context, RecipesAdapterOnClickHandler clickHandler) {
        this.context = context;
        mClickHandler = clickHandler;
    }


    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mRecipeImage;
        public final TextView mRecipeName;
        public final TextView mRecipeServings;

        public RecipesAdapterViewHolder(View view) {
            super(view);
            mRecipeImage = (ImageView) view.findViewById(R.id.iv_recipe_image);
            mRecipeName = (TextView)view.findViewById(R.id.tv_recipe_name);
            mRecipeServings = (TextView)view.findViewById(R.id.tv_recipe_servings) ;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            BakingSample bakingSample = mData.get(adapterPosition);
            mClickHandler.onClick(bakingSample);
        }
    }

    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_recipe_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesAdapterViewHolder recipesAdapterViewHolder, int position) {
        BakingSample bakingSample = mData.get(position);
        recipesAdapterViewHolder.mRecipeName.setText(bakingSample.getName());
        recipesAdapterViewHolder.mRecipeServings.setText(String.format(context.getString(R.string.servings),bakingSample.getServings()));
        Glide.with(context).load(bakingSample.getImage()).centerCrop().placeholder(R.mipmap.ic_cake).into(recipesAdapterViewHolder.mRecipeImage);
    }

    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.size();
    }

    public void setData(ArrayList<BakingSample> bakingSamples) {
        this.mData.clear();
        mData = bakingSamples;
        notifyDataSetChanged();
    }

}
