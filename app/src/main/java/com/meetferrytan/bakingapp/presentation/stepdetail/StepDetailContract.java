package com.meetferrytan.bakingapp.presentation.stepdetail;

import com.meetferrytan.bakingapp.data.entity.Step;
import com.meetferrytan.bakingapp.presentation.base.BaseContract;

/**
 * Created by ferrytan on 9/3/17.
 */

public class StepDetailContract {

    interface View extends BaseContract.View{
        void displayData(String videoUrl, String description, String imageThumbnailUrl);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void updateData(Step step);
    }
}