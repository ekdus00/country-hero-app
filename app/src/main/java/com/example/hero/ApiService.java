package com.example.hero;

import com.example.hero.dto.JobFilterDTO;
import com.example.hero.dto.JobInfoDTO;
import com.example.hero.dto.JobPostCommentRequestDTO;
import com.example.hero.dto.JobPostCommentResponseDTO;
import com.example.hero.dto.JobPostEditResponseDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ApiService {
//    @Multipart
//    @POST("/jobPost/create")
//    Call<Void> JobPostSend(
//            @Part MultipartBody.Part file,
//            @PartMap Map<String, RequestBody> textData
//    );

    @Multipart
    @POST("jobPost/create")
    Call<ResponseBody> JobPostSend(
            @Part("request") RequestBody request,
            @Part MultipartBody.Part image);





//    @POST("jobs/get")
//    Call<Job> getJobDetail(@Body Job jobDetailRequest);

//    @GET("jobList")
//    Call<List<JobListDTO>> JobListSend(@QueryMap Map<String, RequestBody> textData);

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

//    @GET("jobList")
//    Call<List<JobInfoDTO>> JobListSend(@Body JobFilterDTO jobFilterDTO);

//    @GET("jobList")
//    Call<List<JobInfoDTO>> JobListSend(@Body RequestBody request);

//    @POST("jobList")
//    Call<List<JobInfoDTO>> JobListSend(@Body JobFilterDTO jobFilterDTO);

//    @POST("jobList")
//    Call<List<JobInfoDTO>> JobListSend(@Body RequestBody request);

    @POST("job/jobList")
    Call<List<JobInfoDTO>> JobListSend(@Header("Authorization") String authToken, @Body JobFilterDTO jobFilterDTO);


    @GET("/api/comment/job/{selectedJobId}/commentList")
    Call<List<JobPostCommentResponseDTO>> getJobPostCommentList(@Path("selectedJobId") Integer selectedJobId);

    @POST("/api/comment/job")
    Call<List<JobPostCommentResponseDTO>> createJobPostComment(
            @Header("Authorization") String authorization,
            @Body JobPostCommentRequestDTO requestDTO);

//    @GET("/api/comment/job/{selectedJobId}/{selectedCommentId}")
//    Call<JobPostCommentEditResponseDTO> getJobPostComment(
//            @Header("Authorization") String authorization,
//            @Path("selectedJobId") Integer selectedJobId,
//            @Path("selectedCommentId") Integer selectedCommentId);
//
//    @PUT("/api/comment/job")
//    Call<List<JobPostCommentResponseDTO>> updateJobPostComment(
//            @Header("Authorization") String authorization,
//            @Body JobPostCommentUpdateRequestDTO requestDTO);
//
//    @DELETE("/api/comment/job")
//    Call<List<JobPostCommentResponseDTO>> deleteJobPostComment(
//            @Header("Authorization") String authorization,
//            @Body JobPostCommentDeleteRequestDTO requestDTO);




    @GET("jobList")
    Call<List<JobInfoDTO>> getJobList();





    @POST("jobDetail")
    Call<JobPostEditResponseDTO> getJobDetail(@Body RequestBody request);

    @GET("country")
    Call<List<String>> getCountries();

    @GET("/city/{country}")
    Call<List<String>> getCitiesByCountry(@Path("country") String country);

    @GET("cropForm")
    Call<List<String>> getCropForms();

    @GET("/cropType/{selectedCropForm}")
    Call<List<String>> getCropTypes(@Path("selectedCropForm") String cropForm);

}
