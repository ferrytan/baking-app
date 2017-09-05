package com.meetferrytan.bakingapp.presentation.recipelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meetferrytan.bakingapp.R;
import com.meetferrytan.bakingapp.data.entity.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ferrytan on 9/1/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {

    private Context mContext;
    private List<Recipe> data;
    private RecipeItemClickListener mCallback;

    public RecipeListAdapter(Context context, List<Recipe> data, RecipeItemClickListener callback) {
        mContext = context;
        this.data = data;
        mCallback = callback;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recipe, parent, false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        holder.bindView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txvRecipeName)
        TextView txvRecipeName;
        @BindView(R.id.txvServings)
        TextView txvServings;

        public RecipeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final Recipe recipe){
            txvRecipeName.setText(recipe.getName());
            txvServings.setText(String.format(mContext.getString(R.string.label_servings), recipe.getServings()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onRecipeItemClicked(recipe);
                }
            });
        }
    }

    public interface RecipeItemClickListener{
        void onRecipeItemClicked(Recipe recipe);
    }
}
