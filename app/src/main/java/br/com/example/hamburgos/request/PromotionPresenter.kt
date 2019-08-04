package br.com.example.hamburgos.request

import br.com.example.hamburgos.model.Promotion
import br.com.example.hamburgos.ui.PromotionActivity
import br.com.example.hamburgos.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromotionPresenter(private val activity: PromotionActivity?) {

    private val repository: Repository = Repository(activity)

    fun getPromotions() {
        repository.listPromotions(object : Callback<List<Promotion>> {
            override fun onResponse(call: Call<List<Promotion>>, response: Response<List<Promotion>>) {
                if (activity != null) {
                    val promotions = response.body()
                    activity.setContent(promotions)
                }
            }

            override fun onFailure(call: Call<List<Promotion>>, t: Throwable) {
                activity!!.setError(Constants.ERROR)
            }
        })
    }

}