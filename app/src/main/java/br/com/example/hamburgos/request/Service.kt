package br.com.example.hamburgos.request

import br.com.example.hamburgos.model.Ingredient
import br.com.example.hamburgos.model.Promotion
import br.com.example.hamburgos.model.Order
import br.com.example.hamburgos.model.Snack
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface Service {

    @GET("snack")
    fun listSnacks(): Call<List<Snack>>

    @GET("snack/{snackId}")
    fun getSnackById(@Path("snackId") snackId: Int): Call<Snack>

    @GET("ingredient")
    fun listIngredients(): Call<List<Ingredient>>

    @GET("ingredient/{ingredientId}")
    fun getIngredientsById(@Path("ingredientId") idExtra: Int?): Call<Ingredient>

    @GET("snack/{snackId}/ingredient")
    fun getIngredientsBySnack(@Path("snackId") snackId: Int): Call<List<Ingredient>>

    @GET("promotion")
    fun listPromotions(): Call<List<Promotion>>

    @GET("order")
    fun listOrders(): Call<List<Order>>

    @PUT("order/{snackId}")
    fun addOrder(@Path("snackId") snackId: Int, @Body extras: List<Int>): Call<Order>


}
