package br.com.example.hamburgos.request;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.example.hamburgos.model.Ingredient;
import br.com.example.hamburgos.model.Request;
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
                List<Snack> snacks = response.body();
                getIngredientsBySnack(snacks);
            }

            @Override
            public void onFailure(@NonNull Call<List<Snack>> call, Throwable t) {
                activity.setError(t.getMessage());
            }
        });
    }

    private void getIngredientsBySnack(final List<Snack> snacks) {
        for (final Snack snack : snacks){
            repository.getIngredientBySnack(snack.getId(), new Callback<List<Ingredient>>() {
                @Override
                public void onResponse(@NonNull Call<List<Ingredient>> call, @NonNull Response<List<Ingredient>> response) {
                    List<Ingredient> ingredients = response.body();
                    snack.setIngredientList(ingredients);
                    if ( activity != null ){
                        activity.setContent(snacks);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Ingredient>> call, Throwable t) {
                }
            });
        }
    }

    public void addRequest(final Snack snack) {
        repository.addRequest(snack, new Callback<Request>() {
            @Override
            public void onResponse(@NonNull Call<Request> call, @NonNull Response<Request> response) {
                Request request = response.body();
                if ( activity != null ){
                    activity.setRequest(request);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Request> call, Throwable t) {
            }
        });
    }
}
