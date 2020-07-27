package br.com.example.hamburgos.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
        listPromotion.layoutManager = layoutParams
        listPromotion.adapter = adapter

        presenter!!.getPromotions()
    }

    fun setContent(promotions: List<Promotion>?) {
        if (promotions == null || promotions.isEmpty()) {
            textEmptyList.visibility = View.VISIBLE
            listPromotion.visibility = View.GONE
        } else {
            textEmptyList.visibility = View.GONE
            listPromotion.visibility = View.VISIBLE
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
