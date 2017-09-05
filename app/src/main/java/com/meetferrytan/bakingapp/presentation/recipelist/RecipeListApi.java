package com.meetferrytan.bakingapp.presentation.recipelist;

import com.meetferrytan.bakingapp.data.entity.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by ferrytan on 8/30/17.
 */

public interface RecipeListApi {
    @GET("2017/May/59121517_baking/baking.json")
    Observable<List<Recipe>> getAllRecipes();
}
