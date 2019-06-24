package br.com.example.hamburgos.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.example.hamburgos.R;
import br.com.example.hamburgos.listener.IngredientItemClickListener;
import br.com.example.hamburgos.model.Ingredient;
import br.com.example.hamburgos.model.Snack;
import br.com.example.hamburgos.util.Constants;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private List<Ingredient> data;
    private Context context;
    private IngredientItemClickListener clickListener;
    private Snack snack;

    IngredientsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingridient_view, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {

        if (data.isEmpty())
            return;

        final Ingredient ingredient = data.get(position);

        if (ingredient != null && snack != null) {
            holder.name.setText(ingredient.getName());
            holder.quantity.setText(String.format(context.getString(R.string.quantity), getQuantityIngredient(snack, ingredient)));
            holder.price.setText(context.getString(R.string.price, ingredient.getPrice()));
            if ( clickListener != null ) {
                holder.back.setOnClickListener(clickListener.onClick(Constants.BACK, ingredient));
                holder.forward.setOnClickListener(clickListener.onClick(Constants.FORWARD, ingredient));
            }
        }
    }

    private int getQuantityIngredient(Snack snack, Ingredient ingredient){
        int result = 0;
        for (Ingredient snackIngredient: snack.getIngredientList()){
            if ( snackIngredient.getId() == ingredient.getId() ){
                ++result;
            }
        }
        if (snack.getExtras() != null ) {
            for (Ingredient snackExtras : snack.getExtras()) {
                if ( snackExtras.getId() == ingredient.getId() ){
                    ++result;
                }
            }
        }
        return result;
    }


    @Override
    public int getItemCount() {
        return (data != null ? data.size() : 0);
    }


    public void setContent(List<Ingredient> ingredientList, Snack snack) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data.clear();
        }
        if (ingredientList != null) {
            this.data.addAll(ingredientList);
        }
        this.snack = snack;
        notifyDataSetChanged();
    }

    void setClickListener(IngredientItemClickListener listener) {
        this.clickListener = listener;
    }
}
