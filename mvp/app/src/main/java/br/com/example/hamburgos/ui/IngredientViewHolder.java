package br.com.example.hamburgos.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.example.hamburgos.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_ingredient)
    TextView name;
    @BindView(R.id.text_quantity)
    TextView quantity;
    @BindView(R.id.button_back)
    Button back;
    @BindView(R.id.button_forward)
    Button forward;
    @BindView(R.id.text_price)
    TextView price;

    IngredientViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}