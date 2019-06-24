package br.com.example.hamburgos.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.example.hamburgos.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PromotionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_thumbnail)
    ImageView imageThumbnail;
    @BindView(R.id.text_promotion_name)
    TextView promotionName;
    @BindView(R.id.text_description)
    TextView description;

    PromotionViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
