package com.example.githubrepodisplay.service.apiRepository;



import com.example.githubrepodisplay.service.model.UsersList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search/users")
    Call<UsersList> getUsersList(@Query("q") String tech);

}
