package com.meetferrytan.bakingapp.presentation.steplist;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.meetferrytan.bakingapp.BakingApplication;
import com.meetferrytan.bakingapp.R;
import com.meetferrytan.bakingapp.data.component.DaggerActivityInjectorComponent;
import com.meetferrytan.bakingapp.data.entity.Ingredient;
import com.meetferrytan.bakingapp.data.entity.Recipe;
import com.meetferrytan.bakingapp.data.entity.Step;
import com.meetferrytan.bakingapp.presentation.base.BaseActivity;
import com.meetferrytan.bakingapp.presentation.stepdetail.StepDetailActivity;
import com.meetferrytan.bakingapp.presentation.stepdetail.StepDetailFragment;
import com.meetferrytan.bakingapp.widget.BakingWidgetProvider;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class StepListActivity extends BaseActivity<StepListPresenter>
        implements StepListContract.View {
    public static final String BUNDLE_DATA = "data";
    public static final String TAG_FRAGMENT_DETAIL = "detail";
    public static final String KEY_SELECTED_POSITION = "selected_position";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.root)
    View root;
    private Recipe mRecipe;
    private boolean mIsTabletLayout;
    StepDetailFragment mDetailFragment;

    @Inject
    SharedPreferences mPref;
    private IngredientsStepsAdapter mIngredientsStepsAdapter;
    private int mSelectedPosition;

    @Override
    protected void initComponent() {
        mComponent = DaggerActivityInjectorComponent.builder()
                .appComponent(BakingApplication.getAppComponent())
                .build();
        mComponent.inject(this);
    }

    @Override
    public int setLayoutRes() {
        return R.layout.activity_step_list;
    }

    @Override
    public void processIntentExtras(Bundle extras) {
        mRecipe = extras.getParcelable(BUNDLE_DATA);
    }

    @Override
    public void startingUpActivity(Bundle savedInstanceState) {
        mIsTabletLayout = getResources().getBoolean(R.bool.isTabletView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mRecipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mIsTabletLayout) {
            //Restore the fragment's instance
            mDetailFragment = (StepDetailFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_DETAIL);
        }

        if (savedInstanceState != null) {
            mSelectedPosition = savedInstanceState.getInt(KEY_SELECTED_POSITION, IngredientsStepsAdapter.EXTRA_VIEW_COUNT);
        } else {
            mSelectedPosition = IngredientsStepsAdapter.EXTRA_VIEW_COUNT;
        }
        getPresenter().processRecipe(mRecipe);
    }

    @Override
    public void showError(int processCode, int errorCode, String message) {
        // nothing to implement here
    }

    @Override
    public void showLoading(int processCode, boolean show) {
        // nothing to implement here
    }

    @Override
    public void displayIngredientsAndSteps(List<Ingredient> ingredients, List<Step> steps) {
        // open activity
        mIngredientsStepsAdapter = new IngredientsStepsAdapter(this, ingredients, steps, new IngredientsStepsAdapter.StepListCallback() {
            @Override
            public void onStepClicked(Step step, int position) {
                if (mIsTabletLayout) {
                    mDetailFragment.setLastPlayedPosition(0);
                    mDetailFragment.updateStepData(step);
                    mSelectedPosition = position;
                    mIngredientsStepsAdapter.refreshSelectedView(position);
                } else {
                    // open activity
                    Intent intent = new Intent(StepListActivity.this, StepDetailActivity.class);
                    intent.putExtra(StepDetailActivity.BUNDLE_DATA, mRecipe);
                    intent.putExtra(StepDetailActivity.BUNDLE_SELECTED_STEP_ID, step.getId());
                    startActivity(intent);
                }
            }
        });
        recyclerview.setAdapter(mIngredientsStepsAdapter);

        if (mIsTabletLayout) {
            if (mDetailFragment == null) {
                Log.d("StepDetailFragment", "displayIngredientsAndSteps: ");
                mDetailFragment = StepDetailFragment.newInstance(steps.get(0));
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, mDetailFragment, TAG_FRAGMENT_DETAIL)
                    .commit();
            mIngredientsStepsAdapter.refreshSelectedView(mSelectedPosition);
        }
    }

    private void addToHome() {
        List<Ingredient> ingredients = mRecipe.getIngredients();

        String ingredientString = "";
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            ingredientString = ingredientString.concat(
                    String.format(getString(R.string.label_ingredients_each),
                            String.valueOf(ingredient.getQuantity()),
                            ingredient.getMeasure(),
                            ingredient.getIngredient()));
        }
        mPref.edit()
                .putString(BakingWidgetProvider.PREF_RECIPE_NAME, mRecipe.getName())
                .putString(BakingWidgetProvider.PREF_RECIPE_ALL_INGREDIENTS, ingredientString)
                .apply();

        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(),
                        BakingWidgetProvider.class));

        BakingWidgetProvider ingredientWidget = new BakingWidgetProvider();
        ingredientWidget.onUpdate(this, AppWidgetManager.getInstance(this), ids);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        addToHome();
        Snackbar.make(root, R.string.label_add_to_homescreen_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mIngredientsStepsAdapter != null)
            outState.putInt(KEY_SELECTED_POSITION, mSelectedPosition);
    }
}
