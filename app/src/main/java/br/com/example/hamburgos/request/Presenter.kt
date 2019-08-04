package br.com.example.hamburgos.request

import br.com.example.hamburgos.model.Ingredient
import br.com.example.hamburgos.model.Order
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.ui.MainActivity
import br.com.example.hamburgos.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Presenter(private val activity: MainActivity?) {

    private val repository: Repository = Repository(activity)

    fun getSnacks() {
        repository.listSnacks(object : Callback<List<Snack>> {
            override fun onResponse(call: Call<List<Snack>>, response: Response<List<Snack>>) {
                val snacks = response.body()
                if (snacks != null) {
                    getIngredientsBySnack(snacks)
                }
            }

            override fun onFailure(call: Call<List<Snack>>, t: Throwable) {
                activity?.setError(t.message)
            }
        })
    }

    private fun getIngredientsBySnack(snacks: List<Snack>) {
        for (snack in snacks) {
            repository.getIngredientBySnack(snack.id, object : Callback<List<Ingredient>> {
                override fun onResponse(call: Call<List<Ingredient>>, response: Response<List<Ingredient>>) {
                    val ingredients = response.body()
                    snack.ingredientList = ingredients
                    activity?.setContent(snacks)
                }

                override fun onFailure(call: Call<List<Ingredient>>, t: Throwable) {}
            })
        }
    }

    fun addRequest(snack: Snack) {
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
