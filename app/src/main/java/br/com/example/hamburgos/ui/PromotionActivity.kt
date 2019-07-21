package br.com.example.hamburgos.ui

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.example.hamburgos.R
import br.com.example.hamburgos.model.Promotion
import br.com.example.hamburgos.request.PromotionPresenter
import kotlinx.android.synthetic.main.activity_promotion.*

class PromotionActivity : AppCompatActivity() {

    private var adapter: PromotionAdapter? = null
    private var presenter: PromotionPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion)

        presenter = PromotionPresenter(this)
        adapter = PromotionAdapter(this)

        val layoutParams = LinearLayoutManager(this)
        list_promotion!!.layoutManager = layoutParams
        list_promotion!!.adapter = adapter

        presenter!!.getPromotions()
    }

    fun setContent(promotions: List<Promotion>?) {
        if (promotions == null || promotions.isEmpty()) {
            text_empty_list.visibility = View.VISIBLE
            list_promotion!!.visibility = View.GONE
        } else {
            text_empty_list.visibility = View.GONE
            list_promotion!!.visibility = View.VISIBLE
            adapter!!.setContent(promotions)
        }
    }

    fun setError(errorMessage: String) {
        val builder = AlertDialog.Builder(this@PromotionActivity)
        builder.setMessage(errorMessage)
        builder.setPositiveButton("OK") { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.show()
    }
}
