package br.com.example.hamburgos.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.example.hamburgos.R;
import br.com.example.hamburgos.model.Request;

public class RequestAdapter extends RecyclerView.Adapter<SnackViewHolder> {

    private List<Request> data;
    private Context context;
//    private PromotionItemClickListener clickListener;

    RequestAdapter(Context context) {
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

        final Request request = data.get(position);

        if (request != null) {
            holder.snackName.setText(request.getName());
            holder.price.setText(context.getString(R.string.price, request.getPrice()));
            holder.ingredients.setText(request.getIngredientListString());
            Picasso.with(context)
                    .load(request.getImage())
                    .resize(50, 50)
                    .centerCrop()
                    .placeholder(R.drawable.hamburguer)
                    .into(holder.imageThumbnail);
        }
    }


    @Override
    public int getItemCount() {
        return (data != null ? data.size() : 0);
    }

    void setContent(List<Request> requests) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data.clear();
        }
        if (requests != null) {
            this.data.addAll(requests);
        }
        notifyDataSetChanged();
    }

    void clearContent() {
        data.clear();
    }

}
