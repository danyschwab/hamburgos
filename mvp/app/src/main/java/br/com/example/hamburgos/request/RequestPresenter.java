package br.com.example.hamburgos.request;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.example.hamburgos.model.Ingredient;
import br.com.example.hamburgos.model.Request;
import br.com.example.hamburgos.model.Snack;
import br.com.example.hamburgos.ui.RequestActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestPresenter {

    private Repository repository;

    private RequestActivity activity;

    public RequestPresenter(RequestActivity activity) {
        this.activity = activity;
        this.repository = new Repository(activity);
    }


    public void getRequests() {
        repository.listRequests(new Callback<List<Request>>() {
            @Override
            public void onResponse(@NonNull Call<List<Request>> call, @NonNull Response<List<Request>> response) {
                if ( activity != null) {
                    List<Request> requests = response.body();
                    if ( requests != null ) {
                        getSnackById(requests);
                    } else {
                        activity.setContent(requests);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Request>> call, Throwable t) {
                if (activity != null) {
                    activity.setError(t.getMessage());
                }
            }
        });
    }

    private void getSnackById(List<Request> requests) {
        for (final Request request: requests) {
            repository.getSnackById(request.getId_sandwich(), new Callback<Snack>() {
                @Override
                public void onResponse(@NonNull Call<Snack> call, @NonNull Response<Snack> response) {
                    Snack snack = response.body();
                    if (snack != null) {
                        getIngredientsBySnack(request, snack);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Snack> call, Throwable t) {
                    if (activity != null) {
                        activity.setError(t.getMessage());
                    }
                }
            });
        }
    }

    private void getIngredientsBySnack(final Request request, final Snack snack) {
        repository.getIngredientBySnack(snack.getId(), new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(@NonNull Call<List<Ingredient>> call, @NonNull Response<List<Ingredient>> response) {
                List<Ingredient> ingredients = response.body();
                snack.setIngredientList(ingredients);
                if ( activity != null ){
                    request.setSnack(snack);
                    activity.setContent(request);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Ingredient>> call, Throwable t) {
            }
        });

    }


}
