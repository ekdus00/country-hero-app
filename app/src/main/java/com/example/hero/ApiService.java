package com.example.hero;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiService {
    @GET("scrapList")
    Call<Map<String, Response>> getScrapList();
}
