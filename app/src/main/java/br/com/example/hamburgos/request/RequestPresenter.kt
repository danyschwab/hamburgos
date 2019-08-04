package br.com.example.hamburgos.request

import br.com.example.hamburgos.model.Ingredient
import br.com.example.hamburgos.model.Order
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.ui.RequestActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestPresenter(private val activity: RequestActivity?) {

    private val repository: Repository = Repository(activity)

    fun getRequests() {
        repository.listOrders(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (activity != null) {
                    val orders = response.body()
                    if (orders != null) {
                        getSnackById(orders)
                    } else {
                        activity.setContent(orders)
                    }
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                activity?.setError(t.message)
            }
        })
    }

    private fun getSnackById(orders: List<Order>) {
        for (order in orders) {
            repository.getSnackById(order.snackId, object : Callback<Snack> {
                override fun onResponse(call: Call<Snack>, response: Response<Snack>) {
                    val snack = response.body()
                    if (snack != null) {
                        getIngredientsBySnack(order, snack)
                    }
                }

                override fun onFailure(call: Call<Snack>, t: Throwable) {
                    activity?.setError(t.message)
                }
            })
        }
    }

    private fun getIngredientsBySnack(order: Order, snack: Snack) {
        repository.getIngredientBySnack(snack.id, object : Callback<List<Ingredient>> {
            override fun onResponse(call: Call<List<Ingredient>>, response: Response<List<Ingredient>>) {
                val ingredients = response.body()
                snack.ingredientList = ingredients
                if (activity != null) {
                    order.snack = snack
                    activity.setContent(order)
                }
            }

            override fun onFailure(call: Call<List<Ingredient>>, t: Throwable) {}
        })

    }


}
