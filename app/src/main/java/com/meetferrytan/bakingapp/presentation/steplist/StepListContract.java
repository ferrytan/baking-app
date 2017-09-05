package com.meetferrytan.bakingapp.presentation.steplist;

import com.meetferrytan.bakingapp.data.entity.Ingredient;
import com.meetferrytan.bakingapp.data.entity.Recipe;
import com.meetferrytan.bakingapp.data.entity.Step;
import com.meetferrytan.bakingapp.presentation.base.BaseContract;

import java.util.List;

/**
 * Created by ferrytan on 9/1/17.
 */

public class StepListContract {

    interface View extends BaseContract.View{
        void displayIngredientsAndSteps(List<Ingredient> ingredients, List<Step> steps);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void processRecipe(Recipe recipe);
    }
}