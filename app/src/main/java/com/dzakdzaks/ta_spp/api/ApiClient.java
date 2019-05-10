package com.dzakdzaks.ta_spp.api;

import com.dzakdzaks.ta_spp.global.GlobalVariable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit setInit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder().baseUrl(GlobalVariable.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static ApiInterface getInstance(){
        return setInit().create(ApiInterface.class);
    }
}
