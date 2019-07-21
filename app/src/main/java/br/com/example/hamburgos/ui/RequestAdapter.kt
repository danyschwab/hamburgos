package br.com.example.hamburgos.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.example.hamburgos.R
import br.com.example.hamburgos.model.Request
import br.com.example.hamburgos.util.layoutInflater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.request_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class RequestAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    private var data: MutableList<Request>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = context.layoutInflater.inflate(R.layout.request_view, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {

        if (data!!.isEmpty())
            return

        val request = data!![position]

        holder.snackName.text = request.snack!!.name
        holder.price.text =context.getString(R.string.price, request.snack!!.price)
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

    internal fun setContent(requests: List<Request>?) {
        if (this.data == null) {
            this.data = ArrayList()
        } else {
            this.data!!.clear()
        }
        if (requests != null) {
            this.data!!.addAll(requests)
        }
        notifyDataSetChanged()
    }

    internal fun clearContent() {
        data!!.clear()
    }

    internal fun addContent(request: Request) {
        if (this.data == null) {
            this.data = ArrayList()
        }
        this.data!!.add(request)
        notifyDataSetChanged()

    }

    class RequestViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val imageThumbnail: ImageView? = view.image_thumbnail
        val snackName: TextView = view.text_snack_name
        val price: TextView = view.text_price
        val date: TextView = view.text_date
        val ingredients: TextView = view.text_ingredients
    }

}
