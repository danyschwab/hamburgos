package br.com.example.hamburgos.model;

import java.io.Serializable;
import java.util.List;

public class Snack implements Serializable {

    private String previewURL;
    private String snackName;
    private String price;
    private List<Ingredient> ingredientList;

    public String getPreviewURL() {
        return previewURL;
    }

    public String getPrice() {
        return price;
    }

    public String getSnackName() {
        return snackName;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }
}
