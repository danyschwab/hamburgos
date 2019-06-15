package br.com.example.hamburgos.request;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.example.hamburgos.model.Request;
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
                    activity.setContent(requests);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Request>> call, Throwable t) {
                activity.setError("Error occur!");
            }
        });
    }
}
