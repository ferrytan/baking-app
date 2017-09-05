package com.meetferrytan.bakingapp.presentation.recipelist;

import com.meetferrytan.bakingapp.data.entity.Recipe;
import com.meetferrytan.bakingapp.presentation.base.BaseContract;

import java.util.List;

/**
 * Created by ferrytan on 8/30/17.
 */

public interface RecipeListContract {

    interface View extends BaseContract.View{
        void displayRecipes(List<Recipe> recipes);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void getAllRecipes();
    }
}
