package com.example.borisova_animals;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("Animals")
    Call<Animal> createPost(@Body Animal dataModal);
}

