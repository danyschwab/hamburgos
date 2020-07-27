package br.com.example.hamburgos.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import br.com.example.hamburgos.R
import br.com.example.hamburgos.ui.listener.IngredientItemClickListener
import br.com.example.hamburgos.model.Ingredient
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.util.Constants
import br.com.example.hamburgos.util.layoutInflater
import kotlinx.android.synthetic.main.ingridient_view.view.*
import java.util.*

class IngredientsAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var data: MutableList<Ingredient>? = null
    private var clickListener: IngredientItemClickListener? = null
    private var snack: Snack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = context.layoutInflater.inflate(R.layout.ingridient_view, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {

        if (data!!.isEmpty())
            return

        val ingredient = data!![position]

        if (snack != null) {
            holder.name.text = ingredient.name
            holder.quantity.text = (String.format(context.getString(R.string.quantity), getQuantityIngredient(snack!!, ingredient)))
            holder.price.text = (context.getString(R.string.price, ingredient.price))
            if (clickListener != null) {
                holder.back.setOnClickListener(clickListener!!.onClick(Constants.BACK, ingredient))
                holder.forward.setOnClickListener(clickListener!!.onClick(Constants.FORWARD, ingredient))
            }
        }
    }

    private fun getQuantityIngredient(snack: Snack, ingredient: Ingredient): Int {
        var result = 0
        for (snackIngredient in snack.ingredientList!!) {
            //TODO validate this logic
            if (snackIngredient.id == ingredient.id) {
                ++result
            }
        }
        if (snack.extras != null) {
            for (snackExtras in snack.extras!!) {
                if (snackExtras.id == ingredient.id) {
                    ++result
                }
            }
        }
        return result
    }


    override fun getItemCount(): Int {
        return if (data != null) data!!.size else 0
    }


    fun setContent(ingredientList: List<Ingredient>?, snack: Snack) {
        if (this.data == null) {
            this.data = ArrayList()
        } else {
            this.data?.clear()
        }
        if (ingredientList != null) {
            this.data?.addAll(ingredientList)
        }
        this.snack = snack
        notifyDataSetChanged()
    }

    internal fun setClickListener(listener: IngredientItemClickListener) {
        this.clickListener = listener
    }

    class IngredientViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.textIngredient
        val quantity: TextView = view.textQuantity
        val back: Button = view.buttonBack
        val forward: Button = view.buttonForward
        val price: TextView = view.textPrice
    }
}
