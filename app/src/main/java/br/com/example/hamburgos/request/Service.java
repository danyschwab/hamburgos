package br.com.example.hamburgos.request;

import java.util.List;

import br.com.example.hamburgos.model.Ingredient;
import br.com.example.hamburgos.model.Promotion;
import br.com.example.hamburgos.model.Request;
import br.com.example.hamburgos.model.Snack;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface Service {

    @GET("lanche")
    Call<List<Snack>> listSnacks();

    @GET("ingrediente")
    Call<List<Ingredient>> listIngredients();

    @GET("ingrediente/de/{snackId}")
    Call<List<Ingredient>> getIngredientsBySnack(@Path("snackId") int snackId);

    @GET("./promocao")
    Call<List<Promotion>> listPromotions();

    @GET("./pedido")
    Call<List<Request>> listRequest();

    @PUT("pedido/{snackId}")
    Call<Request> addRequest(@Path("snackId") int snackId, @Body List<Ingredient> extras);
}
