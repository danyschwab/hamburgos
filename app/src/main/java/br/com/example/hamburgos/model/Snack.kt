package br.com.example.hamburgos.model

import java.io.Serializable
import java.util.ArrayList

class Snack : Serializable {

    val id: Int = 0
    val image: String? = null
    val name: String? = null

    var ingredientList: List<Ingredient>? = null
    var extras: MutableList<Ingredient>? = null

    val price: Float
        get() {
            var result = 0f
            if (ingredientList != null) {
                for (ingredient in ingredientList!!) {
                    result += ingredient.price
                }
            }
            if (extras != null) {
                for (ingredient in extras!!) {
                    result += ingredient.price
                }
            }

            val meat = howManyIngredientOnList("Hamburguer de carne")
            if (meat > 2 && meat % 3 == 0) {
                result -= (meat * 3).toFloat()
                result += (meat / 3 * 2 * 3).toFloat()
            }

            val cheese = howManyIngredientOnList("Queijo")
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
            val lettuceFlag = checkIfIngredientInList("Alface")
            if (lettuceFlag) {
                result = !checkIfIngredientInList("Bacon")
            }
            return result
        }

    val ingredientListString: String
        get() {
            var result = ""
            if (ingredientList != null) {
                for (ingredient in ingredientList!!) {
                    result += ingredient.name
                    result = "$result, "
                }
            }
            if (extras != null) {
                for (ingredient in extras!!) {
                    result += ingredient.name
                    result = "$result, "
                }
            }
            if (result.length > 2) {
                result = result.substring(0, result.length - 2)
            }
            return result
        }

    val jsonExtras: List<Int>
        get() {
            val idList = ArrayList<Int>()
            for (extra in extras!!) {
                idList.add(extra.id)
            }
            return idList
        }

    private fun checkIfIngredientInList(ingredientName: String?): Boolean {
        var result = false
        if (ingredientName != null) {
            if (ingredientList != null) {
                for (ingredient in ingredientList!!) {
                    if (ingredientName.toLowerCase() == ingredient.name!!.toLowerCase()) {
                        result = true
                        break
                    }
                }
            }
            if (!result && extras != null) {
                for (ingredient in extras!!) {
                    if (ingredientName.toLowerCase() == ingredient.name!!.toLowerCase()) {
                        result = true
                        break
                    }
                }
            }
        }
        return result
    }

    private fun howManyIngredientOnList(ingredientName: String?): Int {
        var result = 0
        if (ingredientName != null) {
            if (ingredientList != null) {
                for (ingredient in ingredientList!!) {
                    if (ingredientName.toLowerCase() == ingredient.name!!.toLowerCase()) {
                        ++result
                    }
                }
            }
            if (extras != null) {
                for (ingredient in extras!!) {
                    if (ingredientName.toLowerCase() == ingredient.name!!.toLowerCase()) {
                        ++result
                    }
                }
            }
        }
        return result
    }

    fun addExtras(ingredient: Ingredient) {
        if (extras == null) {
            extras = ArrayList()
        }
        extras!!.add(ingredient)
    }

    fun removeExtras(ingredient: Ingredient) {
        if (extras != null) {
            extras!!.remove(ingredient)
        }
    }
}
