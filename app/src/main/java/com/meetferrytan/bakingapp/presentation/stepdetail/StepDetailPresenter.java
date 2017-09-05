package com.meetferrytan.bakingapp.presentation.stepdetail;

import com.meetferrytan.bakingapp.data.entity.Step;
import com.meetferrytan.bakingapp.presentation.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by ferrytan on 9/3/17.
 */

public class StepDetailPresenter extends BasePresenter<StepDetailContract.View>
        implements StepDetailContract.Presenter {

    @Inject
    public StepDetailPresenter() {
    }

    @Override
    public void updateData(Step step) {
        getView().displayData(step.getVideoUrl(), step.getDescription(), step.getThumbnailUrl());
    }
}
