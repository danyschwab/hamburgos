package br.com.example.hamburgos.snacklist

import br.com.example.hamburgos.model.Ingredient
import br.com.example.hamburgos.model.Order
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.request.Repository
import br.com.example.hamburgos.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SnackListPresenter(private val view: SnackListContract.View) {

    private val repository: Repository = Repository()

    fun getSnacks() {
        repository.listSnacks(object : Callback<List<Snack>> {
            override fun onResponse(call: Call<List<Snack>>, response: Response<List<Snack>>) {
                response.body()?.let { snacks ->
                    view.setContent(snacks)
                }
            }

            override fun onFailure(call: Call<List<Snack>>, t: Throwable) {
                view.setError(t.message)
            }
        })
    }

    private fun getIngredientsBySnack(snacks: List<Snack>) {
        for (snack in snacks) {
            repository.getIngredientBySnack(snack.id, object : Callback<List<Ingredient>> {
                override fun onResponse(call: Call<List<Ingredient>>, response: Response<List<Ingredient>>) {
                    response.body()?.let {
                        snack.ingredientList = it
                        view.setContent(snacks)
                    }
                }

                override fun onFailure(call: Call<List<Ingredient>>, t: Throwable) {}
            })
        }
    }

    fun addRequest(snack: Snack) {
        repository.addOrder(snack, object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                val request = response.body()
                if (request != null) {
                    view.requestConfirmation()
                } else {
                    view.setError(Constants.ORDER_NOT_COMPLETED)
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                view.setError(t.message)
            }
        })
    }
}
