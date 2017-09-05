package com.meetferrytan.bakingapp.data.component;

import com.meetferrytan.bakingapp.data.util.ActivityScope;
import com.meetferrytan.bakingapp.presentation.recipelist.RecipeListActivity;
import com.meetferrytan.bakingapp.presentation.stepdetail.StepDetailFragment;
import com.meetferrytan.bakingapp.presentation.steplist.StepListActivity;

import dagger.Component;

/**
 * Created by ferrytan on 7/29/17.
 */

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityInjectorComponent {
    void inject (RecipeListActivity recipeListActivity);
    void inject (StepListActivity stepListActivity);
    void inject (StepDetailFragment stepDetailFragment);
}
