package br.com.example.hamburgos.request;

import java.util.Map;

import br.com.example.hamburgos.model.Snacks;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

interface Service {

    @GET("./")
    Call<Snacks> getSnacks(@QueryMap Map<String, String> options);
}
