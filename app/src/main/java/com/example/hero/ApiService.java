package com.example.hero;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;

public interface ApiService {
    @GET("scrapList")
    Call<Map<String, Response>> getScrapList();

    @DELETE("clip/delete")
    Call<Void> deleteClip();
}
