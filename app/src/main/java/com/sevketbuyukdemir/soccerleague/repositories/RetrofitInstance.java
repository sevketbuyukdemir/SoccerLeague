package com.sevketbuyukdemir.soccerleague.repositories;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is hold our API BASE_URL and provide Retrofit object for using Retrofit2 API.
 */
public class RetrofitInstance {
    // TODO change BASE_URL with your API BASE_URL
    public static String BASE_URL = "http://192.168.1.27:8000/";

    /**
     * Retrofit object for using Retrofit2 API
     */
    private static Retrofit retrofit;

    /**
     * This function return class retrofit object, if retrofit object is null, create retrofit
     * object
     * @return retrofit Retrofit (retrofit2 api object)
     */
    public static Retrofit getRetrofitClient() {

        if(retrofit == null ) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}