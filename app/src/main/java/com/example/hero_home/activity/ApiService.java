package com.example.hero_home.activity;

import com.example.hero_home.model.ClipDTO;
import com.example.hero_home.model.ClipDeleteRequestDTO;
import com.example.hero_home.model.LoginDTO;
import com.example.hero_home.model.MatchingDetailResponseDTO;
import com.example.hero_home.model.MatchingListInfoDTO;
import com.example.hero_home.model.MatchingPostCommentRequestDTO;
import com.example.hero_home.model.MatchingPostCommentResponseDTO;
import com.example.hero_home.model.WorkerHomeDTO;

import java.util.List;
import java.util.Map;

import kotlinx.coroutines.scheduling.CoroutineScheduler;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiService {
    @Multipart
    @POST("upload")
    Call<Void> JobPostSend(
            @Part MultipartBody.Part file,
            @PartMap Map<String, RequestBody> textData
    );
    @GET("/api/area/country")
    Call<List<String>> getCountries(@Header("Authorization") String authToken);

    @GET("/api/area/{country}/city")
    Call<List<String>> getCitiesByCountry(@Header("Authorization") String authToken, @Path("country") String country);

    @GET("cropForm")
    Call<List<String>> getCropForms();

    @GET("/cropType/{selectedCropForm}")
    Call<List<String>> getCropTypes(@Path("selectedCropForm") String cropForm);


    @GET("/api/clip")
    Call<List<ClipDTO>> getClipList(@Header("Authorization") String authToken);

    @HTTP(method = "DELETE", path="/api/clip", hasBody = true)
    Call<Void> deleteClip(@Header("Authorization") String authToken, @Body ClipDeleteRequestDTO clipDeleteRequestDTO);

    @HTTP(method = "GET", path="/login", hasBody = true)
    Call<WorkerHomeDTO> login(@Body LoginDTO loginDTO);

    @Multipart
    @POST("/api/matching/matchingPost")
    Call<Void> matchingPost(@Header("Authorization") String authToken, @Part("request") RequestBody requestBody,
                            @Part MultipartBody.Part uploadImg);


    @GET("/api/matching/matchingDetail/{matchingId}")
    Call<MatchingDetailResponseDTO> getMatchingDetail(@Header("Authorization") String authToken, @Path("matchingId") int matchingId);

    @POST("/api/matching/matchingList")
    Call<List<MatchingListInfoDTO>> getMatchingList(@Header("Authorization") String authToken);

    @POST("/api/comment/matching")
    Call<List<MatchingPostCommentResponseDTO>> matchingCommentPost(@Header("Authorization") String authToken, @Body MatchingPostCommentRequestDTO matchingPostCommentRequestDTO);

    @GET("/api/comment/matching/{selectedMatchingId}/commentList")
    Call<List<MatchingPostCommentResponseDTO>> getMatchingCommentList(@Header("Authorization") String authToken, @Path("selectedMatchingId") int selectedMatchingId);

}
