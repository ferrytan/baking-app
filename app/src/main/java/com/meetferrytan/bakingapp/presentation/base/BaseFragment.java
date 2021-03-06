package com.meetferrytan.bakingapp.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meetferrytan.bakingapp.data.component.ActivityInjectorComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ferrytan on 8/2/17.
 */

public abstract class BaseFragment<P extends BaseContract.Presenter>
        extends Fragment implements BaseContract.View {
    private P mPresenter;
    protected ActivityInjectorComponent mComponent;
    Unbinder unbinder;

    protected abstract void initComponent();

    protected abstract View createLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract void processArguments(Bundle args);
    public abstract void startingUpFragment(View view, Bundle savedInstanceState);

    protected P getPresenter() {
        return mPresenter;
    }

    @Inject
    public void setPresenter(P presenter){
        this.mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // load layout
        View view = createLayout(inflater, container, savedInstanceState);

        // inject butter knife to init view
        unbinder = ButterKnife.bind(this, view);

        initComponent();
        // call init action
        processArguments(getArguments());
        startingUpFragment(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.detachView();
    }
}