package com.meetferrytan.bakingapp.presentation.recipelist;

import com.meetferrytan.bakingapp.data.entity.Recipe;
import com.meetferrytan.bakingapp.presentation.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by ferrytan on 8/30/17.
 */

public class RecipeListPresenter extends BasePresenter<RecipeListContract.View>
        implements RecipeListContract.Presenter {
    private RecipeListApi mApi;

    @Inject
    public RecipeListPresenter(Retrofit retrofit) {
        mApi = retrofit.create(RecipeListApi.class);
    }

    @Override
    public void getAllRecipes() {
        Disposable disposable = mApi.getAllRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Recipe>>() {
                    @Override
                    public void accept(@NonNull List<Recipe> recipes) throws Exception {
                        getView().showLoading(0, false);
                        getView().displayRecipes(recipes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        getView().showLoading(0, false);
                        getView().showError(0, -1, throwable.getMessage());
                    }
                });
        addDisposable(disposable);
    }
}
