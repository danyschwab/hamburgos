package br.com.example.hamburgos.model

import br.com.example.hamburgos.util.Constants
import java.io.Serializable
import java.util.*

class Snack : Serializable {

    val id: Int = 0
    val image: String? = null
    val name: String = ""

    var ingredientList: List<Ingredient> = listOf()
    var extras: MutableList<Ingredient> = mutableListOf()

    val price: Float
        get() {
            var result = 0f
            for (ingredient in ingredientList) {
                result += ingredient.price
            }

            for (ingredient in extras) {
                result += ingredient.price
            }


            val meat = howManyIngredientOnList(Constants.HAMBURGER)
            if (meat > 2 && meat % 3 == 0) {
                result -= (meat * 3).toFloat()
                result += (meat / 3 * 2 * 3).toFloat()
            }

            val cheese = howManyIngredientOnList(Constants.CHEESE)
            if (cheese > 2 && cheese % 3 == 0) {
                result -= (cheese * 1.5).toFloat()
                result += (cheese / 3 * 2 * 1.5).toFloat()
            }

            if (isLight) {
                result *= 0.90.toFloat()
            }
            return result
        }

    private val isLight: Boolean
        get() {
            var result = false
            val lettuceFlag = checkIfIngredientInList(Constants.LETTUCE)
            if (lettuceFlag) {
                result = !checkIfIngredientInList("Bacon")
            }
            return result
        }

    val ingredientListString: String
        get() {
            var result = ""

            for (ingredient in ingredientList) {
                result += ingredient.name
                result = "$result, "
            }

            for (ingredient in extras) {
                result += ingredient.name
                result = "$result, "
            }
            if (result.length > 2) {
                result = result.substring(0, result.length - 2)
            }
            return result
        }

    val jsonExtras: List<Int>
        get() {
            return extras.map { id }
        }

    private fun checkIfIngredientInList(ingredientName: String): Boolean {
        for (ingredient in ingredientList) {
            if (ingredientName.toLowerCase() == ingredient.name.toLowerCase()) {
                return true
            }

        }
        for (ingredient in extras) {
            if (ingredientName.toLowerCase() == ingredient.name.toLowerCase()) {
                return true
            }
        }
        return false
    }

    private fun howManyIngredientOnList(ingredientName: String): Int {
        var result = 0
        for (ingredient in ingredientList) {
            if (ingredientName.toLowerCase() == ingredient.name.toLowerCase()) {
                ++result
            }
        }
        for (ingredient in extras) {
            if (ingredientName.toLowerCase() == ingredient.name.toLowerCase()) {
                ++result
            }
        }
        return result
    }

    fun addExtras(ingredient: Ingredient) {
        extras.add(ingredient)
    }

    fun removeExtras(ingredient: Ingredient) {
        extras.remove(ingredient)
    }
}
