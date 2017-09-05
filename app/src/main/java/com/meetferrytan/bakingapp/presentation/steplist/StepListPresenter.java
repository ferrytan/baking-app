package com.meetferrytan.bakingapp.presentation.steplist;

import com.meetferrytan.bakingapp.data.entity.Recipe;
import com.meetferrytan.bakingapp.presentation.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by ferrytan on 9/1/17.
 */

public class StepListPresenter extends BasePresenter<StepListContract.View>
        implements StepListContract.Presenter {

    @Inject
    public StepListPresenter() {
    }

    @Override
    public void processRecipe(Recipe recipe) {
        // do something here
        getView().displayIngredientsAndSteps(recipe.getIngredients(), recipe.getSteps());
    }
}
