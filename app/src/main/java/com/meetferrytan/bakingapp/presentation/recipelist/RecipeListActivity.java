package com.meetferrytan.bakingapp.presentation.recipelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meetferrytan.bakingapp.BakingApplication;
import com.meetferrytan.bakingapp.R;
import com.meetferrytan.bakingapp.data.component.DaggerActivityInjectorComponent;
import com.meetferrytan.bakingapp.data.entity.Recipe;
import com.meetferrytan.bakingapp.presentation.base.BaseActivity;
import com.meetferrytan.bakingapp.presentation.steplist.StepListActivity;

import java.util.List;

import butterknife.BindView;

public class RecipeListActivity extends BaseActivity<RecipeListPresenter>
        implements RecipeListContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loader)
    ProgressBar loader;
    @BindView(R.id.txvRetry)
    TextView txvRetry;

    @Override
    protected void initComponent() {
        mComponent = DaggerActivityInjectorComponent.builder()
                .appComponent(BakingApplication.getAppComponent())
                .build();
        mComponent.inject(this);
    }

    @Override
    public int setLayoutRes() {
        return R.layout.activity_recipe_list;
    }

    @Override
    public void processIntentExtras(Bundle extras) {

    }

    @Override
    public void startingUpActivity(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        int spanSize = getResources().getInteger(R.integer.recipe_grid_span_size);
        recyclerview.setLayoutManager(new GridLayoutManager(this, spanSize));

        getPresenter().getAllRecipes();
    }

    @Override
    public void showError(int processCode, int errorCode, String message) {
        txvRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading(int processCode, boolean show) {
        loader.setVisibility(show ? View.VISIBLE : View.GONE);
        txvRetry.setVisibility(View.GONE);
    }

    @Override
    public void displayRecipes(List<Recipe> recipes) {
        RecipeListAdapter adapter = new RecipeListAdapter(this, recipes, new RecipeListAdapter.RecipeItemClickListener() {
            @Override
            public void onRecipeItemClicked(Recipe recipe) {
                Intent intent = new Intent(RecipeListActivity.this, StepListActivity.class);
                intent.putExtra(StepListActivity.BUNDLE_DATA, recipe);
                startActivity(intent);
            }
        });

        recyclerview.setAdapter(adapter);
    }
}
