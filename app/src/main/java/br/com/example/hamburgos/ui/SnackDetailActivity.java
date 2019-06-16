package br.com.example.hamburgos.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.example.hamburgos.R;
import br.com.example.hamburgos.listener.IngredientItemClickListener;
import br.com.example.hamburgos.model.Ingredient;
import br.com.example.hamburgos.model.Snack;
import br.com.example.hamburgos.request.CustomPresenter;
import br.com.example.hamburgos.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SnackDetailActivity extends AppCompatActivity {

    @BindView(R.id.list_extras)
    RecyclerView recyclerView;
    @BindView(R.id.image_thumbnail)
    ImageView imageThumbnail;
    @BindView(R.id.text_snack_name)
    TextView snackName;
    @BindView(R.id.text_price)
    TextView price;
    @BindView(R.id.text_ingredients)
    TextView ingredients;
    @BindView(R.id.button_done)
    Button done;

    private Unbinder unbinder;
    private CustomPresenter presenter;
    private IngredientsAdapter adapter;

    private Snack snack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_detail);

        unbinder = ButterKnife.bind(this);

        int snackId = getIntent().getIntExtra(Constants.SNACK, 0);

        presenter = new CustomPresenter(this);
        adapter = new IngredientsAdapter(this);

        adapter.setClickListener(new IngredientItemClickListener() {
            @Override
            public View.OnClickListener onClick(final String type, final Ingredient ingredient) {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ( Constants.BACK.equals(type)){
                            snack.removeExtras(ingredient);
                        } else if ( Constants.FORWARD.equals(type)) {
                            snack.addExtras(ingredient);
                        }
                        price.setText(getString(R.string.price, snack.getPrice()));
                        adapter.notifyDataSetChanged();
                    }
                };
            }
        });


        presenter.getSnackById(snackId);
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public void setContent(Snack snack){
        this.snack = snack;
        snackName.setText(snack.getName());
        price.setText(getString(R.string.price, snack.getPrice()));
        ingredients.setText(snack.getIngredientListString());

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(snack.getImage()).error(R.drawable.hamburguer).into(imageThumbnail);

        LinearLayoutManager layoutParams = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutParams);

        recyclerView.setAdapter(adapter);

        done.setOnClickListener(confirmationClickListner);

        presenter.getIngredients();
    }

    public void setContent(List<Ingredient> ingredientList){
        adapter.setContent(ingredientList, snack);
    }

    public void setError(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SnackDetailActivity.this);
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void requestConfirmation(){
        Intent intent = new Intent(SnackDetailActivity.this, RequestActivity.class);
        startActivity(intent);
        finish();
    }

    private View.OnClickListener confirmationClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SnackDetailActivity.this);
            builder.setTitle(getString(R.string.confirmation) + " - " + snack.getName());
            builder.setMessage(snack.getIngredientListString() + getString(R.string.your_way));
            builder.setPositiveButton(R.string.label_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    presenter.addRequest(snack);
                }
            });
            builder.setNegativeButton(R.string.label_no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    };
}
