package br.com.example.hamburgos.request

import br.com.example.hamburgos.model.Ingredient
import br.com.example.hamburgos.model.Order
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.ui.SnackDetailActivity
import br.com.example.hamburgos.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomPresenter(private val activity: SnackDetailActivity?) {

    private val repository: Repository = Repository(activity)

    fun getSnackById(snackId: Int) {
        repository.getSnackById(snackId, object : Callback<Snack> {
            override fun onResponse(call: Call<Snack>, response: Response<Snack>) {
                val snack = response.body()
                if (snack != null) {
                    getIngredientsBySnack(snack)
                }
            }

            override fun onFailure(call: Call<Snack>, t: Throwable) {
                activity?.setError(t.message)
            }
        })
    }

    private fun getIngredientsBySnack(snack: Snack) {
        repository.getIngredientBySnack(snack.id, object : Callback<List<Ingredient>> {
            override fun onResponse(call: Call<List<Ingredient>>, response: Response<List<Ingredient>>) {
                val ingredients = response.body()
                snack.ingredientList = ingredients
                activity?.setContent(snack)
            }

            override fun onFailure(call: Call<List<Ingredient>>, t: Throwable) {}
        })

    }

    fun getIngredients() {
        repository.listIngredients(object : Callback<List<Ingredient>> {
            override fun onResponse(call: Call<List<Ingredient>>, response: Response<List<Ingredient>>) {
                val ingredients = response.body()

                activity?.setContent(ingredients!!)
            }

            override fun onFailure(call: Call<List<Ingredient>>, t: Throwable) {
                activity?.setError(t.message)
            }
        })
    }

    fun addRequest(snack: Snack?) {
        repository.addOrder(snack, object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                val request = response.body()

                if (activity != null) {
                    if (request != null) {
                        activity.requestConfirmation()
                    } else {
                        activity.setError(Constants.ORDER_NOT_COMPLETED)
                    }
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                activity?.setError(t.message)
            }
        })
    }
}
