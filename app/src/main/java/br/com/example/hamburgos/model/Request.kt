package br.com.example.hamburgos.model

import java.io.Serializable

class Request : Serializable {

    val id: Int = 0
    val id_sandwich: Int = 0
    var snack: Snack? = null
    val date: Long = 0
    val extras: List<Int>? = null
    private val extrasIngredients: List<Ingredient>? = null

}
