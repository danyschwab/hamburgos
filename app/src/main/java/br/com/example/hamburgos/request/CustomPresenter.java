package br.com.example.hamburgos.request;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.example.hamburgos.model.Ingredient;
import br.com.example.hamburgos.model.Snack;
import br.com.example.hamburgos.ui.MainActivity;
import br.com.example.hamburgos.ui.SnackDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomPresenter {

    private Repository repository;
    private SnackDetailActivity activity;

    public CustomPresenter(SnackDetailActivity activity) {
        this.activity = activity;
        this.repository = new Repository(activity);
    }

    public void getSnackById(int snackId) {
        repository.getSnackById(snackId, new Callback<Snack>() {
            @Override
            public void onResponse(@NonNull Call<Snack> call, @NonNull Response<Snack> response) {
                Snack snack = response.body();
                if ( snack != null ) {
                    getIngredientsBySnack(snack);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Snack> call, Throwable t) {
                activity.setError(t.getMessage());
            }
        });
    }

    private void getIngredientsBySnack(final Snack snack) {
        repository.getIngredientBySnack(snack.getId(), new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(@NonNull Call<List<Ingredient>> call, @NonNull Response<List<Ingredient>> response) {
                List<Ingredient> ingredients = response.body();
                snack.setIngredientList(ingredients);
                if ( activity != null ){
                    activity.setContent(snack);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Ingredient>> call, Throwable t) {
            }
        });

    }

    public void getIngredients() {
        repository.listIngridients(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(@NonNull Call<List<Ingredient>> call, @NonNull Response<List<Ingredient>> response) {
                List<Ingredient> ingredients = response.body();

                if ( activity != null ){
                    activity.setContent(ingredients);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Ingredient>> call, Throwable t) {
                activity.setError(t.getMessage());
            }
        });
    }
}
