package br.com.example.hamburgos.model;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {

    private int id;
    private int id_sandwich;
    private Snack snack;
    private long date;
    private List<Integer> extras;
    private List<Ingredient> extrasIngredients;


    public int getId() {
        return id;
    }

    public Snack getSnack() {
        return snack;
    }

    public long getDate() {
        return date;
    }

    public int getId_sandwich() {
        return id_sandwich;
    }

    public void setSnack(Snack snack) {
        this.snack = snack;
    }

    public List<Integer> getExtras() {
        return extras;
    }

}
