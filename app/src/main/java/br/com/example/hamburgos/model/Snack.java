package br.com.example.hamburgos.model;

import java.io.Serializable;
import java.util.List;

public class Snack implements Serializable {

    private int id;
    private String image;
    private String name;
    private List<Ingredient> ingredientList;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public static Snack getSnackExample(){
        Snack snack = new Snack();
        snack.id = 1;
        snack.image = "https://goo.gl/W9WdaF";
        snack.name =  "X-Bacon";
        return snack;
    }
}
