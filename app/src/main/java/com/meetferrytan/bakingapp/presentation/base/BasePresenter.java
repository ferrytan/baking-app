package com.meetferrytan.bakingapp.presentation.base;


import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private WeakReference<V> viewRef;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    @Override
    public void attachView(V mvpView) {
        viewRef = new WeakReference<>(mvpView);
    }

    @Override
    public void detachView() {
        mCompositeDisposable.clear();
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    protected void addDisposable(Disposable disposable) {
        this.mCompositeDisposable.add(disposable);
    }
}