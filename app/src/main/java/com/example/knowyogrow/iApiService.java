package com.example.knowyogrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface iApiService {

    @GET("strains/search/all")
    Call<Map<String,Strain>> getAll();

}
