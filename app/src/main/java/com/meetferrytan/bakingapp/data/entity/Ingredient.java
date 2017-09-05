package com.meetferrytan.bakingapp.data.entity;

/**
 * Created by ferrytan on 8/30/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 {
     "quantity": 2,
     "measure": "CUP",
     "ingredient": "Graham Cracker crumbs"
 }
 */
public class Ingredient implements Parcelable{
    @SerializedName("quantity")
    private double mQuantity;
    @SerializedName("measure")
    private String mMeasure;
    @SerializedName("ingredient")
    private String mIngredient;

    public double getQuantity() {
        return mQuantity;
    }

    public void setQuantity(double quantity) {
        mQuantity = quantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String measure) {
        mMeasure = measure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String ingredient) {
        mIngredient = ingredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.mQuantity);
        dest.writeString(this.mMeasure);
        dest.writeString(this.mIngredient);
    }

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.mQuantity = in.readDouble();
        this.mMeasure = in.readString();
        this.mIngredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
