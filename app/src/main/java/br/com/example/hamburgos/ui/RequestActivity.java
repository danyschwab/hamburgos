package br.com.example.hamburgos.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.example.hamburgos.R;
import br.com.example.hamburgos.model.Promotion;
import br.com.example.hamburgos.model.Request;
import br.com.example.hamburgos.request.PromotionPresenter;
import br.com.example.hamburgos.request.RequestPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RequestActivity extends AppCompatActivity {

    @BindView(R.id.list_request)
    RecyclerView recyclerView;

    private RequestAdapter adapter;
    private RequestPresenter presenter;

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        unbinder = ButterKnife.bind(this);

        presenter = new RequestPresenter(this);
        adapter = new RequestAdapter(this);

        LinearLayoutManager layoutParams = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutParams);

        recyclerView.setAdapter(adapter);

        presenter.getRequests();
    }


    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public void setContent(List<Request> requests) {
        adapter.setContent(requests);
    }

    public void setError(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RequestActivity.this);
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }
}
