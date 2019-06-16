package br.com.example.hamburgos.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.example.hamburgos.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_thumbnail)
    ImageView imageThumbnail;
    @BindView(R.id.text_snack_name)
    TextView snackName;
    @BindView(R.id.text_price)
    TextView price;
    @BindView(R.id.text_date)
    TextView date;
    @BindView(R.id.text_ingredients)
    TextView ingredients;


    RequestViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
