package br.com.example.hamburgos.request;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.example.hamburgos.model.Promotion;
import br.com.example.hamburgos.ui.PromotionActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromotionPresenter {

    private Repository repository;

    private PromotionActivity activity;

    public PromotionPresenter(PromotionActivity activity) {
        this.activity = activity;
        this.repository = new Repository(activity);
    }


    public void getPromotions() {
        repository.listPromotions(new Callback<List<Promotion>>() {
            @Override
            public void onResponse(@NonNull Call<List<Promotion>> call, @NonNull Response<List<Promotion>> response) {
                if ( activity != null) {
                    List<Promotion> promotions = response.body();
                    activity.setContent(promotions);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Promotion>> call, Throwable t) {
                activity.setError("Error occur!");
            }
        });
    }

}