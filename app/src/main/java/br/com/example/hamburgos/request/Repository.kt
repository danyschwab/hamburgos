package br.com.example.hamburgos.request

import br.com.example.hamburgos.model.Ingredient
import br.com.example.hamburgos.model.Order
import br.com.example.hamburgos.model.Promotion
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.util.Constants
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository internal constructor() {

    private val service: Service

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(Service::class.java)
    }

    internal fun listSnacks(callback: Callback<List<Snack>>) {
        val call = service.listSnacks()
        call.enqueue(callback)
    }

    internal fun getSnackById(snackId: Int, callback: Callback<Snack>) {
        val call = service.getSnackById(snackId)
        call.enqueue(callback)
    }

    internal fun getIngredientBySnack(snackId: Int, callback: Callback<List<Ingredient>>) {
        val call = service.getIngredientsBySnack(snackId)
        call.enqueue(callback)
    }

    internal fun getIngredientById(id: Int, callback: Callback<Ingredient>) {
        val call = service.getIngredientsById(id)
        call.enqueue(callback)
    }

    internal fun listIngredients(callback: Callback<List<Ingredient>>) {
        val call = service.listIngredients()
        call.enqueue(callback)
    }

    internal fun listPromotions(callback: Callback<List<Promotion>>) {
        val call = service.listPromotions()
        call.enqueue(callback)
    }

    internal fun listOrders(callback: Callback<List<Order>>) {
        val call = service.listOrders()
        call.enqueue(callback)
    }

    internal fun addOrder(snack: Snack, callback: Callback<Order>) {
        val call = service.addOrder(snack.id, snack.jsonExtras)
        call.enqueue(callback)
    }

}
