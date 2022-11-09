package com.example.borisova_animals;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPIUpdate {
    @PUT("Animals/")
    Call<Animal> updateData(@Query("Id")int id, @Body Animal dataModal);
}
