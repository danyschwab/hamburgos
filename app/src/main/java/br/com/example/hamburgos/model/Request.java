package br.com.example.hamburgos.model;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {

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
        for (Ingredient ingredient : ingredientList){
            result += ingredient.getPrice();
        }
        for (Ingredient ingredient : extras){
            result += ingredient.getPrice();
        }
        return result;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public String getIngredientListString(){
        String result = "";
        for(Ingredient ingredient: ingredientList){
            result += ingredient.getName() + ", ";
        }
        for(Ingredient ingredient: extras){
            result += ingredient.getName() + ", ";
        }
        result = result.substring(0, result.length()-2);
        return result;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Ingredient> getExtras() {
        return extras;
    }

}
