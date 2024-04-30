package com.example.hero;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @Multipart
    @POST("upload")
    Call<Void> JobPostSend(
            @Part MultipartBody.Part file,
            @PartMap Map<String, RequestBody> textData
    );

    @GET("country")
    Call<List<String>> getCountries();

    @GET("/city/{country}")
    Call<List<String>> getCitiesByCountry(@Path("country") String country);

    @GET("cropForm")
    Call<List<String>> getCropForms();

    @GET("/cropType/{selectedCropForm}")
    Call<List<String>> getCropTypes(@Path("selectedCropForm") String cropForm);

}
