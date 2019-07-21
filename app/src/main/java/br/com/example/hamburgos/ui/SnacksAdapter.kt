package br.com.example.hamburgos.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import br.com.example.hamburgos.R
import br.com.example.hamburgos.listener.SnackItemClickListener
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.util.Constants
import br.com.example.hamburgos.util.layoutInflater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.snack_view.view.*
import java.util.*

class SnacksAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<SnacksAdapter.SnackViewHolder>() {

    private var data: MutableList<Snack>? = null
    private var clickListener: SnackItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackViewHolder {
        val view = context.layoutInflater.inflate(R.layout.snack_view, parent, false)
        return SnackViewHolder(view)
    }

    override fun onBindViewHolder(holder: SnackViewHolder, position: Int) {

        if (data!!.isEmpty())
            return

        val snack = data!![position]

        holder.snackName.text =snack.name
        holder.price.text = context.getString(R.string.price, snack.price)
        holder.ingredients.text = snack.ingredientListString
        Picasso.get()
                .load(snack.image)
                .resize(50, 50)
                .centerCrop()
                .placeholder(R.drawable.hamburguer)
                .into(holder.imageThumbnail)
        holder.add.setOnClickListener(clickListener!!.onClick(Constants.ADD, snack))
        holder.custom.setOnClickListener(clickListener!!.onClick(Constants.CUSTOM, snack))
    }

    override fun getItemCount(): Int {
        return if (data != null) data!!.size else 0
    }

    internal fun setContent(snacks: List<Snack>?) {
        if (this.data == null) {
            this.data = ArrayList()
        } else {
            this.data!!.clear()
        }
        if (snacks != null) {
            this.data!!.addAll(snacks)
        }
        notifyDataSetChanged()
    }

    internal fun clearContent() {
        data!!.clear()
    }

    internal fun setClickListener(listener: SnackItemClickListener) {
        this.clickListener = listener
    }

    class SnackViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val imageThumbnail: ImageView = view.image_thumbnail
        val snackName: TextView = view.text_snack_name
        val price: TextView = view.text_price
        val ingredients: TextView = view.text_ingredients
        val add: Button = view.button_add
        val custom: Button = view.button_custom

    }

}