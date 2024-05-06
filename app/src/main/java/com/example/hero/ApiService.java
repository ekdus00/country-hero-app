package com.example.hero;

import com.example.hero.object.JobFilterDTO;
import com.example.hero.object.JobInfoDTO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface ApiService {
    @Multipart
    @POST("/jobPost/create")
    Call<Void> JobPostSend(
            @Part MultipartBody.Part file,
            @PartMap Map<String, RequestBody> textData
    );


//    @POST("jobs/get")
//    Call<Job> getJobDetail(@Body Job jobDetailRequest);

//    @GET("jobList")
//    Call<List<JobListDTO>> JobListSend(@QueryMap Map<String, RequestBody> textData);

//    @GET("jobList")
//    Call<List<JobInfoDTO>> JobListSend(@Body JobFilterDTO jobFilterDTO);

//    @GET("jobList")
//    Call<List<JobInfoDTO>> JobListSend(
//            @Query("userId") String userId,
//            @Query("userType") String userType,
//            @Query("area") String area,
//            @Query("startWorkDate") String startWorkDate,
//            @Query("endWorkDate") String endWorkDate,
//            @Query("payGoe") Integer payGoe,
//            @Query("payLoe") Integer payLoe,
//            @Query("keyWord") String keyWord,
//            @Query("sortType") String sortType,
//            @Query("userLatitude") Double userLatitude,
//            @Query("userLongitude") Double userLongitude
//            );

    @GET("jobList")
    Call<List<JobInfoDTO>> JobListSend(@Body JobFilterDTO jobFilterDTO);



    @GET("country")
    Call<List<String>> getCountries();

    @GET("/city/{country}")
    Call<List<String>> getCitiesByCountry(@Path("country") String country);

    @GET("cropForm")
    Call<List<String>> getCropForms();

    @GET("/cropType/{selectedCropForm}")
    Call<List<String>> getCropTypes(@Path("selectedCropForm") String cropForm);

}
