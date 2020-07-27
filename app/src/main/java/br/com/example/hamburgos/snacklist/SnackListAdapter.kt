package br.com.example.hamburgos.snacklist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.example.hamburgos.R
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.util.Constants
import br.com.example.hamburgos.util.layoutInflater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.snack_view.view.*
import java.util.*

class SnackListAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<SnackListAdapter.SnackViewHolder>() {

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
        if ( snack.image != "") {
            Picasso.get()
                    .load(snack.image)
                    .resize(50, 50)
                    .centerCrop()
                    .placeholder(R.drawable.hamburguer)
                    .into(holder.imageThumbnail)
        }
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
            this.data?.clear()
        }
        if (snacks != null) {
            this.data?.addAll(snacks)
        }
        notifyDataSetChanged()
    }

    internal fun clearContent() {
        data?.clear()
    }

    internal fun setClickListener(listener: SnackItemClickListener) {
        this.clickListener = listener
    }

    class SnackViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val imageThumbnail: ImageView = view.imageThumbnail
        val snackName: TextView = view.textSnackName
        val price: TextView = view.textPrice
        val ingredients: TextView = view.textIngredients
        val add: Button = view.buttonAdd
        val custom: Button = view.buttonCustom

    }

}