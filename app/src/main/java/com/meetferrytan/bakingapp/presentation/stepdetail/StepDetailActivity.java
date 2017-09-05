package com.meetferrytan.bakingapp.presentation.stepdetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.meetferrytan.bakingapp.R;
import com.meetferrytan.bakingapp.data.entity.Recipe;
import com.meetferrytan.bakingapp.data.entity.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity {

    public static final String BUNDLE_DATA = "data";
    public static final String BUNDLE_SELECTED_STEP_ID = "selected_step_id";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout slidingTabs;
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        Bundle extras = getIntent().getExtras();
        final Recipe recipe = extras.getParcelable(BUNDLE_DATA);
        final int selectedStepId = extras.getInt(BUNDLE_SELECTED_STEP_ID);
        List<Step> steps = recipe.getSteps();
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (Step step : steps) {
            StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(step);
            fragments.add(stepDetailFragment);
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(mSectionsPagerAdapter);
        slidingTabs.setupWithViewPager(viewPager);

        Log.d("test", "onCreate: " + selectedStepId);
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(selectedStepId);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.label_section_tab, position);
        }
    }
}
