package br.com.example.hamburgos.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.example.hamburgos.R;
import br.com.example.hamburgos.model.Promotion;
import br.com.example.hamburgos.request.PromotionPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PromotionActivity extends AppCompatActivity {

    @BindView(R.id.list_promotions)
    RecyclerView recyclerView;

    private PromotionAdapter adapter;
    private PromotionPresenter presenter;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        unbinder = ButterKnife.bind(this);

        presenter = new PromotionPresenter(this);
        adapter = new PromotionAdapter(this);

        LinearLayoutManager layoutParams = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutParams);

        recyclerView.setAdapter(adapter);

        presenter.getPromotions();
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public void setContent(List<Promotion> promotions) {
        adapter.setContent(promotions);
    }

    public void setError(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PromotionActivity.this);
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }
}
