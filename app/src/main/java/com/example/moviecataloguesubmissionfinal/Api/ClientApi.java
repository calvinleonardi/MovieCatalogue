package com.example.moviecataloguesubmissionfinal.Api;

import com.example.moviecataloguesubmissionfinal.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {
    public static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient httpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl httpUrl = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                        .build();

                request = request.newBuilder().url(httpUrl).build();
                return chain.proceed(request);
            }
        }).build();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
