package com.example.knowyogrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface iApiService {

    @GET("strains/search/all")
    Call<Map<String,Strain>> getAll();

    @GET("searchdata/effects")
    Call<ArrayList<Effect>> getEffects();

    @GET("searchdata/flavors")
    Call<ArrayList<String>> getFlavors();

    @GET("strains/data/desc/{id}")
    Call<Description> getDescription(@Path("id") int id);

    @POST("login")
    Call<Acceso> tryLogin(@Body LoginData loginData);


}
