package com.rudainc.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BakingSample implements Parcelable {
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private String servings;
    private String image;

    public BakingSample(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, String servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    protected BakingSample(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readString();
        image = in.readString();
    }

    public static final Creator<BakingSample> CREATOR = new Creator<BakingSample>() {
        @Override
        public BakingSample createFromParcel(Parcel in) {
            return new BakingSample(in);
        }

        @Override
        public BakingSample[] newArray(int size) {
            return new BakingSample[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public String getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(servings);
        parcel.writeString(image);
    }
}
