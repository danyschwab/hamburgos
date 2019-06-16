package br.com.example.hamburgos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Snack implements Serializable {

    private int id;
    private String image;
    private String name;

    private List<Ingredient> ingredientList;
    private List<Ingredient> extras;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public float getPrice(){
        float result = 0;
        if ( ingredientList != null ) {
            for (Ingredient ingredient : ingredientList) {
                result += ingredient.getPrice();
            }
        }
        if ( extras != null) {
            for (Ingredient ingredient : extras) {
                result += ingredient.getPrice();
            }
        }


        return result;
    }

//    private boolean isLight(){
//        boolean result = false;
//        if ( ingredientList != null ) {
//                    }
//        if ( extras != null) {
//            for (Ingredient ingredient : extras) {
//                result += ingredient.getPrice();
//            }
//        }
//    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public String getIngredientListString(){
        String result = "";
        if ( ingredientList != null ) {
            for (Ingredient ingredient : ingredientList) {
                result += ingredient.getName() + ", ";
            }
        }
        if ( extras != null) {
            for (Ingredient ingredient : extras) {
                result += ingredient.getName() + ", ";
            }
        }
        if ( result.length() > 2) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Ingredient> getExtras() {
        return extras;
    }

    public void setExtras(List<Ingredient> extras) {
        this.extras = extras;
    }

    public void addExtras(Ingredient ingredient){
        if (extras == null ){
            extras = new ArrayList<>();
        }
        extras.add(ingredient);
    }

    public void removeExtras(Ingredient ingredient) {
        if ( extras != null ) {
            extras.remove(ingredient);
        }
    }
}
