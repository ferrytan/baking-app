package com.meetferrytan.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.meetferrytan.bakingapp.presentation.recipelist.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by ferrytan on 9/5/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityIntentTest {

    public static final String BUNDLE_DATA = "data";

    @Rule
    public IntentsTestRule<RecipeListActivity> intentsTestRule
            = new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void clickOnRecyclerViewItem_runsStepListActivityIntent() {

        onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        intended(allOf(
                hasExtraWithKey(BUNDLE_DATA)
        ));
    }


}