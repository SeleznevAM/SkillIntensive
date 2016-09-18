package com.example.alex.skillintensive.data.network;

import com.example.alex.skillintensive.data.network.interceptors.HeaderInterceptor;
import com.example.alex.skillintensive.util.AppConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *класс для создания Rest-сервиса
 */
public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder sBuilder = new Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
/*
Метод для создания сервиса
 */
    public static <S> S createService(Class<S> serviceClass){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(new HeaderInterceptor());
        httpClient.addInterceptor(logging);

        Retrofit retrofit = sBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);

    }
}
