package br.com.example.hamburgos.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.example.hamburgos.R;
import br.com.example.hamburgos.model.Request;

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder> {

    private List<Request> data;
    private Context context;

    RequestAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_view, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {

        if (data.isEmpty())
            return;

        final Request request = data.get(position);

        if (request != null) {
            holder.snackName.setText(request.getSnack().getName());
            holder.price.setText(context.getString(R.string.price, request.getSnack().getPrice()));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault());
            Date date = new Date();
            date.setTime(request.getDate());
            holder.date.setText(String.format(context.getString(R.string.request_date), formatter.format(date)));
            holder.ingredients.setText(request.getSnack().getIngredientListString());
            Picasso.get()
                    .load(request.getSnack().getImage())
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

    void addContent(Request request) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(request);
        notifyDataSetChanged();

    }
}
