package br.com.example.hamburgos.listener

import android.view.View

import br.com.example.hamburgos.model.Ingredient

abstract class IngredientItemClickListener {

    abstract fun onClick(type: String, ingredient: Ingredient): View.OnClickListener
}
