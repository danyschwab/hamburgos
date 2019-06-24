package br.com.example.hamburgos.listener;

import android.view.View;

import br.com.example.hamburgos.model.Ingredient;

public abstract class IngredientItemClickListener {

    public abstract View.OnClickListener onClick(String type, Ingredient ingredient);
}
