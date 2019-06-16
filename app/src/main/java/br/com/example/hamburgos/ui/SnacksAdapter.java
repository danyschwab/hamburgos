package br.com.example.hamburgos.ui;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.example.hamburgos.R;
import br.com.example.hamburgos.listener.SnackItemClickListener;
import br.com.example.hamburgos.model.Snack;
import br.com.example.hamburgos.util.Constants;

public class SnacksAdapter extends RecyclerView.Adapter<SnackViewHolder> {

    private List<Snack> data;
    private Context context;
    private SnackItemClickListener clickListener;

    SnacksAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SnackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.snack_view, parent, false);
        return new SnackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SnackViewHolder holder, int position) {

        if (data.isEmpty())
            return;

        final Snack snack = data.get(position);

        if (snack != null) {
            holder.snackName.setText(snack.getName());
            holder.price.setText(context.getString(R.string.price, snack.getPrice()));
            holder.ingredients.setText(snack.getIngredientListString());
            Picasso.get()
                    .load(snack.getImage())
                    .resize(50, 50)
                    .centerCrop()
                    .placeholder(R.drawable.hamburguer)
                    .into(holder.imageThumbnail);
            holder.add.setOnClickListener(clickListener.onClick(Constants.ADD, snack));
            holder.custom.setOnClickListener(clickListener.onClick(Constants.CUSTOM, snack));
        }
    }


    @Override
    public int getItemCount() {
        return (data != null ? data.size() : 0);
    }

    void setContent(List<Snack> snacks) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data.clear();
        }
        if (snacks != null) {
            this.data.addAll(snacks);
        }
        notifyDataSetChanged();
    }

    void clearContent() {
        data.clear();
    }

    void setClickListener(SnackItemClickListener listener) {
        this.clickListener = listener;
    }
}