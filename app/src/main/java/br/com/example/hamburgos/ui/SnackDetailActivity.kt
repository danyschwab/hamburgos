package br.com.example.hamburgos.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.example.hamburgos.R
import br.com.example.hamburgos.listener.IngredientItemClickListener
import br.com.example.hamburgos.model.Ingredient
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.request.CustomPresenter
import br.com.example.hamburgos.util.Constants
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_snack_detail.*

class SnackDetailActivity : AppCompatActivity() {

    private var presenter: CustomPresenter? = null
    private var adapter: IngredientsAdapter? = null

    private var snack: Snack? = null

    private val confirmationClickListener = View.OnClickListener {
        val builder = AlertDialog.Builder(this@SnackDetailActivity)
        builder.setTitle(getString(R.string.confirmation) + " - " + snack!!.name)
        builder.setMessage(snack!!.ingredientListString + getString(R.string.your_way))
        builder.setPositiveButton(R.string.label_yes) { _, _ -> presenter!!.addRequest(snack) }
        builder.setNegativeButton(R.string.label_no) { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack_detail)

        val snackId = intent.getIntExtra(Constants.SNACK, 0)

        presenter = CustomPresenter(this)
        adapter = IngredientsAdapter(this)

        adapter!!.setClickListener(object : IngredientItemClickListener() {
            override fun onClick(type: String, ingredient: Ingredient): View.OnClickListener {
                return View.OnClickListener {
                    if (Constants.BACK == type) {
                        snack!!.removeExtras(ingredient)
                    } else if (Constants.FORWARD == type) {
                        snack!!.addExtras(ingredient)
                    }
                    text_price.text = getString(R.string.price, snack!!.price)
                    adapter!!.notifyDataSetChanged()
                }
            }
        })

        presenter!!.getSnackById(snackId)
    }

    fun setContent(snack: Snack) {
        this.snack = snack
        text_snack_name.text = snack.name
        text_price.text = getString(R.string.price, snack.price)
        text_ingredients.text = snack.ingredientListString

        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this))
        builder.build().load(snack.image).error(R.drawable.hamburguer).into(image_thumbnail)

        val layoutParams = LinearLayoutManager(this)
        list_extras.layoutManager = layoutParams
        list_extras.adapter = adapter

        button_done.setOnClickListener(confirmationClickListener)

        presenter!!.getIngredients()
    }

    fun setContent(ingredientList: List<Ingredient>) {
        adapter!!.setContent(ingredientList, snack!!)
    }

    fun setError(errorMessage: String?) {
        val builder = AlertDialog.Builder(this@SnackDetailActivity)
        builder.setMessage(errorMessage)
        builder.setPositiveButton("OK") { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.show()
    }

    fun requestConfirmation() {
        val intent = Intent(this@SnackDetailActivity, RequestActivity::class.java)
        startActivity(intent)
        finish()
    }
}
