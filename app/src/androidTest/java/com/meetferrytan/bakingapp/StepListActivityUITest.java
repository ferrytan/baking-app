package com.meetferrytan.bakingapp;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.meetferrytan.bakingapp.data.entity.Recipe;
import com.meetferrytan.bakingapp.presentation.steplist.StepListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assume.assumeTrue;

/**
 * Created by ferrytan on 9/5/17.
 */

@RunWith(AndroidJUnit4.class)
public class StepListActivityUITest {

    public static final String BUNDLE_DATA = "data";
    private static final int STEP_WITH_VIDEO = 3;
    private static final int STEP_WITHOUT_VIDEO = 4;

    public static final String RECIPE_GSON = "{\n" +
            "  \"id\": 4,\n" +
            "  \"name\": \"Cheesecake\",\n" +
            "  \"ingredients\": [\n" +
            "    {\n" +
            "      \"quantity\": 2,\n" +
            "      \"measure\": \"CUP\",\n" +
            "      \"ingredient\": \"Graham Cracker crumbs\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"steps\": [\n" +
            "    {\n" +
            "      \"id\": 0,\n" +
            "      \"shortDescription\": \"Recipe Introduction\",\n" +
            "      \"description\": \"Recipe Introduction\",\n" +
            "      \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdae8_-intro-cheesecake/-intro-cheesecake.mp4\",\n" +
            "      \"thumbnailURL\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"shortDescription\": \"Recipe Introduction\",\n" +
            "      \"description\": \"Recipe Introduction\",\n" +
            "      \"videoURL\": \"\",\n" +
            "      \"thumbnailURL\": \"\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"servings\": 8,\n" +
            "  \"image\": \"\"\n" +
            "}";
    @Rule
    public ActivityTestRule<StepListActivity> activityTestRule =
            new ActivityTestRule<>(StepListActivity.class, true, false);

    @Test
    public void clickOnRecyclerViewItem_opensRecipeStepActivity() {
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(RECIPE_GSON, Recipe.class);
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_DATA, recipe);
        activityTestRule.launchActivity(intent);
        assumeTrue(isScreenSw600dp());
        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.txvDescription))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStepWithoutVideo_showsNoVideoImageView() {
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(RECIPE_GSON, Recipe.class);
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_DATA, recipe);
        activityTestRule.launchActivity(intent);
        assumeTrue(isScreenSw600dp());
        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(STEP_WITHOUT_VIDEO, click()));

        onView(
                allOf(
                        withId(R.id.imgNoVideo),
                        isDisplayed()))
                .check(matches(isDisplayed()));
    }

    private boolean isScreenSw600dp() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activityTestRule.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        float heightDp = displayMetrics.heightPixels / displayMetrics.density;
        float screenSw = Math.min(widthDp, heightDp);
        return screenSw >= 600;
    }
}