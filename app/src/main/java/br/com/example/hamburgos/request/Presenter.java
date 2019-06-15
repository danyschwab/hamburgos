package br.com.example.hamburgos.request;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.example.hamburgos.model.Snack;
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
        repository.listSnacks(new Callback<List<Snack>>() {
            @Override
            public void onResponse(@NonNull Call<List<Snack>> call, @NonNull Response<List<Snack>> response) {
                if (activity != null) {
                    List<Snack> snacks = response.body();
                    activity.setContent(snacks);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Snack>> call, Throwable t) {
                activity.setError("Error occur!");
            }
        });
    }
}
