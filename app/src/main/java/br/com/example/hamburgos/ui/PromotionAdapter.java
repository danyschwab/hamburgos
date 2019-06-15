package br.com.example.hamburgos.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.example.hamburgos.R;
import br.com.example.hamburgos.model.Promotion;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionViewHolder> {

    private List<Promotion> data;
    private Context context;
//    private PromotionItemClickListener clickListener;

    PromotionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_view, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PromotionViewHolder holder, int position) {

        if (data.isEmpty())
            return;

        final Promotion promotion = data.get(position);

        if (promotion != null) {
            holder.promotionName.setText(promotion.getName());
            holder.description.setText(promotion.getDescription());
//            Picasso.with(context)
//                    .load(promotion.getImage())
//                    .resize(50, 50)
//                    .centerCrop()
//                    .placeholder(R.drawable.hamburguer)
//                    .into(holder.imageThumbnail);
        }
    }


    @Override
    public int getItemCount() {
        return (data != null ? data.size() : 0);
    }

    void setContent(List<Promotion> promotions) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data.clear();
        }
        if (promotions != null) {
            this.data.addAll(promotions);
        }
        notifyDataSetChanged();
    }

    void clearContent() {
        data.clear();
    }

//    void setClickListener(SnackItemClickListener listener) {
//        this.clickListener = listener;
//    }
}