package br.com.example.hamburgos.request;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.example.hamburgos.model.Snack;
import br.com.example.hamburgos.model.Snacks;
import br.com.example.hamburgos.ui.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter {

    private Repository repository;
    private MainActivity activity;

    public Presenter(MainActivity activity) {
        this.activity = activity;
        this.repository = new Repository(activity);
    }


    public void getSnacks() {
        repository.getSnacks(new Callback<Snacks>() {
            @Override
            public void onResponse(@NonNull Call<Snacks> call, @NonNull Response<Snacks> response) {
                if (activity != null) {
                    List<Snack> snacks = new ArrayList<>();
                    Snacks body = response.body();
                    if (  body != null ) {
                        snacks = body.getSnacks();
                    }
                    activity.setContent(snacks);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Snacks> call, Throwable t) {
            }
        });
    }
}
