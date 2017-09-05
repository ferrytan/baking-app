package com.meetferrytan.bakingapp.data.entity;

/**
 * Created by ferrytan on 8/30/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 {
     "id": 1,
     "name": "Nutella Pie",
     "ingredients": [],
     "steps": [],
     "servings": 8,
     "image": ""
 }
 */
public class Recipe implements Parcelable{
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("ingredients")
    private List<Ingredient> mIngredients;
    @SerializedName("steps")
    private List<Step> mSteps;
    @SerializedName("servings")
    private int mServings;
    @SerializedName("image")
    private String mImage;

    public Recipe() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int servings) {
        mServings = servings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeTypedList(this.mIngredients);
        dest.writeTypedList(this.mSteps);
        dest.writeInt(this.mServings);
        dest.writeString(this.mImage);
    }

    protected Recipe(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.mSteps = in.createTypedArrayList(Step.CREATOR);
        this.mServings = in.readInt();
        this.mImage = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
