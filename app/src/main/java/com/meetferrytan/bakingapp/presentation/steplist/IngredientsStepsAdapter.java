package com.meetferrytan.bakingapp.presentation.steplist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meetferrytan.bakingapp.R;
import com.meetferrytan.bakingapp.data.entity.Ingredient;
import com.meetferrytan.bakingapp.data.entity.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ferrytan on 9/1/17.
 */

public class IngredientsStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEWTYPE_INGREDIENT = 0;
    public static final int VIEWTYPE_STEP = 1;
    public static final int VIEWTYPE_TITLE = 2;

    public static final int EXTRA_VIEW_COUNT = 3;

    private Context mContext;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;
    private StepListCallback mCallback;

    private int selectedPosition;

    public IngredientsStepsAdapter(Context context, List<Ingredient> ingredients, List<Step> steps, StepListCallback callback) {
        mContext = context;
        mIngredients = ingredients;
        mSteps = steps;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEWTYPE_INGREDIENT:
                View viewIngredient = LayoutInflater.from(mContext).inflate(R.layout.item_ingredient, parent, false);
                return new IngredientViewHolder(viewIngredient);
            case VIEWTYPE_STEP:
                View viewStep = LayoutInflater.from(mContext).inflate(R.layout.item_step, parent, false);
                return new StepViewHolder(viewStep);
            case VIEWTYPE_TITLE:
                View viewTitle = LayoutInflater.from(mContext).inflate(R.layout.item_title, parent, false);
                return new TitleViewHolder(viewTitle);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEWTYPE_INGREDIENT:
                ((IngredientViewHolder) holder).bindView();
                break;
            case VIEWTYPE_STEP:
                ((StepViewHolder) holder).bindView(getStepItem(position));
                break;
            case VIEWTYPE_TITLE:
                ((TitleViewHolder) holder).bindView(position == 0 ? R.string.label_ingredients : R.string.label_steps);
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size() + EXTRA_VIEW_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEWTYPE_TITLE;
        } else if (position == 1) {
            return VIEWTYPE_INGREDIENT;
        } else if (position == 2) {
            return VIEWTYPE_TITLE;
        }
        return VIEWTYPE_STEP;
    }

    public void refreshSelectedView(int newSelectedPosition){
        notifyItemChanged(selectedPosition);
        selectedPosition = newSelectedPosition;
        notifyItemChanged(selectedPosition);
    }

    public Step getStepItem(int adapterPosition) {
        return mSteps.get(adapterPosition - EXTRA_VIEW_COUNT);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txvIngredients)
        TextView txvIngredients;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView() {
            String ingredientString = "";
            for (Ingredient ingredient : mIngredients) {
                ingredientString = ingredientString.concat(
                        String.format(mContext.getString(R.string.label_ingredients_each),
                                String.valueOf(ingredient.getQuantity()),
                                ingredient.getMeasure(),
                                ingredient.getIngredient()));
            }
            txvIngredients.setText(ingredientString);
        }
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txvTitle)
        TextView txvTitle;
        @BindView(R.id.imgVideoAvailable)
        ImageView imgVideoAvailable;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final Step step) {
            txvTitle.setText(String.format(mContext.getString(R.string.label_step_each), String.valueOf(step.getId()), step.getShortDescription()));
            imgVideoAvailable.setVisibility(step.getVideoUrl().isEmpty() ? View.GONE : View.VISIBLE);
            itemView.setSelected(getAdapterPosition() == selectedPosition);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onStepClicked(step, getAdapterPosition());
                }
            });
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txvTitle)
        TextView txvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(int titleRes) {
            txvTitle.setText(titleRes);
        }
    }

    public interface StepListCallback {
        void onStepClicked(Step step, int position);
    }
}
