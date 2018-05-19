package com.example.aryawirasandi.whatsapp.services;

import com.example.aryawirasandi.whatsapp.models.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("whatsapp/get.php")
    Call<User> getUser(@Query("username") String username);

    @FormUrlEncoded
    @POST("whatsapp/login.php")
    Call<User> login(@Field("username") String nama, @Field("password")String password);


    public final String BASE_URL = "http://192.168.1.10/";

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    ApiService service = retrofit.create(ApiService.class);


}
