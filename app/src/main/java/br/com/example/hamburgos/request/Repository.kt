package br.com.example.hamburgos.request

import android.content.Context
import br.com.example.hamburgos.model.Ingredient
import br.com.example.hamburgos.model.Order
import br.com.example.hamburgos.model.Promotion
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.util.Constants
import br.com.example.hamburgos.util.Utils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class Repository internal constructor(private val context: Context) {
    private val service: Service

    init {

        //setup cache
        val httpCacheDirectory = File(context.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val interceptor = Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            if (Utils.isNetworkAvailable(this@Repository.context)) {
                val maxAge = 60 // read from cache for 1 minute
                originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
            }
        }

        val client = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
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
