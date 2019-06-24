package br.com.example.hamburgos.model;

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

        int meat = howManyIngredientOnList("Hamburguer de carne");
        if ( meat > 2 &&  meat %3 == 0 ){
            result -= (meat *3);
            result += (((meat/3) *2) * 3);
        }

        int cheese = howManyIngredientOnList("Queijo");
        if ( cheese > 2 &&  cheese %3 == 0 ){
            result -= (cheese *1.5);
            result += (((cheese/3) *2) * 1.5);
        }

        if ( isLight() ){
            result = result * (float)0.90;
        }
        return result;
    }

    private boolean isLight(){
        boolean result = false;
        boolean lettuceFlag = checkIfIngredientInList("Alface");
        if ( lettuceFlag ){
            result = !checkIfIngredientInList("Bacon");
        }
        return result;
    }

    private boolean checkIfIngredientInList(String ingredientName){
        boolean result = false;
        if ( ingredientName!= null ) {
            if (ingredientList != null) {
                for (Ingredient ingredient : ingredientList) {
                    if (ingredientName.toLowerCase().equals(ingredient.getName().toLowerCase())) {
                        result = true;
                        break;
                    }
                }
            }
            if (!result && extras != null) {
                for (Ingredient ingredient : extras) {
                    if (ingredientName.toLowerCase().equals(ingredient.getName().toLowerCase())) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    private int howManyIngredientOnList(String ingredientName){
        int  result = 0;
        if ( ingredientName!= null ) {
            if (ingredientList != null) {
                for (Ingredient ingredient : ingredientList) {
                    if (ingredientName.toLowerCase().equals(ingredient.getName().toLowerCase())) {
                        ++result;
                    }
                }
            }
            if (extras != null) {
                for (Ingredient ingredient : extras) {
                    if (ingredientName.toLowerCase().equals(ingredient.getName().toLowerCase())) {
                        ++result;
                    }
                }
            }
        }
        return result;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public String getIngredientListString(){
        String result = "";
        if ( ingredientList != null ) {
            for (Ingredient ingredient : ingredientList) {
                result = result.concat(ingredient.getName());
                result = result.concat(", ");
            }
        }
        if ( extras != null) {
            for (Ingredient ingredient : extras) {
                result = result.concat(ingredient.getName());
                result = result.concat(", ");
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
        if ( extras == null ){
            extras = new ArrayList<>();
        }
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

    public List<Integer> getJsonExtras() {
        List<Integer> idList = new ArrayList<>();
        for (Ingredient extra: extras){
            idList.add(extra.getId());
        }
        return idList;
    }
}
