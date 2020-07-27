package br.com.example.hamburgos.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.example.hamburgos.R
import br.com.example.hamburgos.model.Order
import br.com.example.hamburgos.util.layoutInflater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.request_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class RequestAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    private var data: MutableList<Order>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = context.layoutInflater.inflate(R.layout.request_view, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {

        if (data!!.isEmpty())
            return

        val request = data!![position]

        holder.snackName.text = request.snack!!.name
        holder.price.text = context.getString(R.string.price, request.snack!!.price)
        val formatter = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
        val date = Date()
        date.time = request.date
        holder.date.text = String.format(context.getString(R.string.request_date), formatter.format(date))
        holder.ingredients.text = request.snack!!.ingredientListString
        Picasso.get()
                .load(request.snack!!.image)
                .resize(50, 50)
                .centerCrop()
                .placeholder(R.drawable.hamburguer)
                .into(holder.imageThumbnail)

    }


    override fun getItemCount(): Int {
        return if (data != null) data!!.size else 0
    }

    internal fun setContent(orders: List<Order>?) {
        if (this.data == null) {
            this.data = ArrayList()
        } else {
            this.data!!.clear()
        }
        if (orders != null) {
            this.data!!.addAll(orders)
        }
        notifyDataSetChanged()
    }

    internal fun clearContent() {
        data!!.clear()
    }

    internal fun addContent(order: Order) {
        if (this.data == null) {
            this.data = ArrayList()
        }
        this.data!!.add(order)
        notifyDataSetChanged()

    }

    class RequestViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val imageThumbnail: ImageView? = view.imageThumbnail
        val snackName: TextView = view.textSnackName
        val price: TextView = view.textPrice
        val date: TextView = view.textDate
        val ingredients: TextView = view.textIngredients
    }

}
