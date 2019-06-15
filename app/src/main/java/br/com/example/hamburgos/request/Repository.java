package br.com.example.hamburgos.request;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.example.hamburgos.BuildConfig;
import br.com.example.hamburgos.model.Ingredient;
import br.com.example.hamburgos.model.Snack;
import br.com.example.hamburgos.util.Constants;
import br.com.example.hamburgos.util.Utils;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private Context context;
    private Service service;

    public Repository(Context context) {

        this.context = context;

        //setup cache
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        final Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                if (Utils.isNetworkAvailable(Repository.this.context)) {
                    int maxAge = 60; // read from cache for 1 minute
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);
    }

    private Map<String, String> createParans() {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.KEY, BuildConfig.API_KEY);
//        map.put(Constants.QUERY, query);

        return map;
    }

    public void listSnacks(Callback<List<Snack>> callback) {
        Call<List<Snack>> call = service.listSnacks();
        call.enqueue(callback);
    }

    public void listIngridients(Callback<List<Ingredient>> callback) {
        Call<List<Ingredient>> call = service.listIngredients();
        call.enqueue(callback);
    }

}
