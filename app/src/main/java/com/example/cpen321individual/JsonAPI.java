package com.example.cpen321individual;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonAPI {

    @GET("ip")
    Call<Post> getPostIp();

    @GET("time")
    Call<Post> getPostTime();

    @GET("name")
    Call<Post> getPostName();

}
