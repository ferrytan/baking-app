package com.meetferrytan.bakingapp.presentation.base;

/**
 * Created by ferrytan on 7/4/17.
 */

public interface BaseContract {

    public interface View{
        void showError(int processCode, int errorCode, String message);
        void showLoading(int processCode, boolean show);
    }

    public interface Presenter<V extends View>{
        V getView();
        void attachView(V mvpView);
        void detachView();
    }
}
