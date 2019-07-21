package br.com.example.hamburgos.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import br.com.example.hamburgos.R
import br.com.example.hamburgos.model.Promotion
import br.com.example.hamburgos.util.layoutInflater
import kotlinx.android.synthetic.main.promotion_view.view.*
import java.util.*

class PromotionAdapter(private val context: Context) : RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder>() {

    private var data: MutableList<Promotion>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {
        val view = context.layoutInflater.inflate(R.layout.promotion_view, parent, false)
        return PromotionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {

        if (data!!.isEmpty())
            return

        val promotion = data!![position]

        holder.promotionName.text = promotion.name
        holder.description.text = promotion.description
        //            Picasso.with(context)
        //                    .load(promotion.getImage())
        //                    .resize(50, 50)
        //                    .centerCrop()
        //                    .placeholder(R.drawable.hamburguer)
        //                    .into(holder.imageThumbnail);

    }


    override fun getItemCount(): Int {
        return if (data != null) data!!.size else 0
    }

    internal fun setContent(promotions: List<Promotion>?) {
        if (this.data == null) {
            this.data = ArrayList()
        } else {
            this.data!!.clear()
        }
        if (promotions != null) {
            this.data!!.addAll(promotions)
        }
        notifyDataSetChanged()
    }

    internal fun clearContent() {
        data!!.clear()
    }

    //    void setClickListener(SnackItemClickListener listener) {
    //        this.clickListener = listener;
    //    }

    class PromotionViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.image_thumbnail
        val promotionName = view.text_promotion_name
        val description =  view.text_description
    }
}