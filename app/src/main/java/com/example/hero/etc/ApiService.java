package com.example.hero.etc;

import com.example.hero.clip.dto.ClipDTO;
import com.example.hero.clip.dto.ClipDeleteRequestDTO;
import com.example.hero.employer.dto.EmployResponseDTO;
import com.example.hero.employer.dto.WorkerInfoDTO;
import com.example.hero.job.dto.JobFilterDTO;
import com.example.hero.job.dto.JobInfoDTO;
import com.example.hero.job.dto.JobPostCommentDeleteRequestDTO;
import com.example.hero.job.dto.JobPostCommentEditResponseDTO;
import com.example.hero.job.dto.JobPostCommentRequestDTO;
import com.example.hero.job.dto.JobPostCommentResponseDTO;
import com.example.hero.job.dto.JobDetailResponseDTO;
import com.example.hero.job.dto.JobPostCommentUpdateRequestDTO;
import com.example.hero.home.dto.OwnerHomeDTO;
import com.example.hero.home.dto.WorkerHomeDTO;
import com.example.hero.job.dto.OwnerInfoResponseDTO;
import com.example.hero.job.dto.ParticipateRequestDTO;
import com.example.hero.login.dto.ExtraInfoDTO;
import com.example.hero.login.dto.JoinRequestDTO;
import com.example.hero.login.dto.LoginRequestDTO;
import com.example.hero.login.dto.LoginResultDTO;
import com.example.hero.login.dto.RefreshTokenRequestDTO;
import com.example.hero.matching.dto.MatchingDetailResponseDTO;
import com.example.hero.matching.dto.MatchingListInfoDTO;
import com.example.hero.matching.dto.MatchingPostCommentRequestDTO;
import com.example.hero.matching.dto.MatchingPostCommentResponseDTO;
import com.example.hero.mypage.dto.OwnerUserInfoUpdateRequestDTO;
import com.example.hero.review.dto.BlockRequestDTO;
import com.example.hero.review.dto.OwnerReviewInfoDTO;
import com.example.hero.review.dto.OwnerReviewUpdateRequestDTO;
import com.example.hero.review.dto.WorkerReviewInfoDTO;
import com.example.hero.review.dto.WorkerReviewUpdateRequestDTO;
import com.example.hero.worker.dto.ParticipateDeleteRequestDTO;
import com.example.hero.worker.dto.ParticipateResponseDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ApiService {

    //공고작성
    @Multipart
    @POST("/api/job/jobPost")
    Call<ResponseBody> JobPostSend(
            @Part("request") RequestBody requestBody,
            @Part MultipartBody.Part uploadImg
    );

    //공고목록
    @POST("/api/job/jobList")
    Call<List<JobInfoDTO>> JobListSend(@Body JobFilterDTO jobFilterDTO);

    //공고상세페이지
    @GET("/api/job/jobDetail/{selectedJobId}")
    Call<JobDetailResponseDTO> getJobDetail(@Path("selectedJobId") int selectedJobId);

    //구인자정보
    @GET("/api/job/{selectedJobId}/ownerInfo")
    Call<OwnerInfoResponseDTO> getOwnerInfo(@Path("selectedJobId") int selectedJobId);

    //공고댓글 작성
    @POST("/api/comment/job")
    Call<Void> createJobPostComment(
            @Body JobPostCommentRequestDTO requestDTO
    );

    //공고댓글 조회
    @GET("/api/comment/job/{selectedJobId}/commentList")
    Call<List<JobPostCommentResponseDTO>> getJobPostCommentList(
            @Path("selectedJobId") int selectedJobId
    );

    //공고댓글 수정조회
    @GET("/api/comment/job/{selectedJobId}/{selectedCommentId}")
    Call<JobPostCommentEditResponseDTO> getJobPostComment(
            @Path("selectedJobId") int selectedJobId,
            @Path("selectedCommentId") int selectedCommentId
    );

    //공고댓글 수정
    @PUT("/api/comment/job")
    Call<List<JobPostCommentResponseDTO>> updateJobPostComment(
            @Body JobPostCommentUpdateRequestDTO requestDTO
    );

    //공고댓글 삭제
    @DELETE("/api/comment/job")
    Call<List<JobPostCommentResponseDTO>> deleteJobPostComment(
            @Body JobPostCommentDeleteRequestDTO requestDTO
    );

    //공고지원
    @POST("/api/participate")
    Call<Void> JobDetailParticipate(
            @Body ParticipateRequestDTO requestDTO
    );




    //홈화면(구직자)
    @GET("/api/home/workerHome")
    Call<WorkerHomeDTO> getWorkerHome();

    //홈화면(구인자)
    @GET("/api/home/ownerHome")
    Call<OwnerHomeDTO> getOwnerHome();





    //지원현황
    @GET("/api/participate")
    Call<ParticipateResponseDTO> getParticipateList();

    //지원취소
    @DELETE("/api/participate")
    Call<Void> DeleteParticipate(@Body ParticipateDeleteRequestDTO requestDTO);

    //나의공고
    @GET("/api/employ/employList")
    Call<EmployResponseDTO> getEmployList();

    //공고현황
    @GET("/api/employ/employList/{selectedJobPost}/workerList")
    Call<List<WorkerInfoDTO>> getWorkerList(@Path("selectedJobPost") int jobPostId);

//    //공고조회(공고수정)
//    @GET("/api/job/JobPost/{selectedJobPost}/update")
//    Call<JobPostDTO> getEditJobPost(@Path("selectedJobPost") int jobId);
//
//    //공고수정
//    @Multipart
//    @PUT("/api/job/JobPost/update")
//    Call<Void> updateJobPost(@Header(@PartMap Map<String, String> requestDTO, @Part MultipartBody.Part uploadImgFile);
//
//    //공고마감
//    @PUT("/api/job/JobPost/close")
//    Call<Void> closeJobPost(@Header(@Body JobRequestDTO requestDTO);
//


    //구인자 상호평가완료/목록
    @PUT("/api/review/owner/complete")
    Call<Void> updateOwnerReview(@Body OwnerReviewUpdateRequestDTO requestDTO);

    //구직자 차단
    @POST("/api/block")
    Call<Void> ReviewBlock(
            @Body BlockRequestDTO requestDTO
    );

    //구직자 상호평가완료
    @PUT("/api/review/worker/complete")
    Call<Void> updateWorkerReview(@Body WorkerReviewUpdateRequestDTO requestDTO);

    //구인자 상호평가 목록
    @GET("/api/review/owner")
    Call<List<OwnerReviewInfoDTO>> getOwnerReviewList();

    //구직자 상호평가 목록
    @GET("/api/review/worker")
    Call<List<WorkerReviewInfoDTO>> getWorkerReviewList();


    //회원정보수정(구인자)
    @PUT("/api/user/userInfo/owner")
    Call<Void> updateOwnerInfo(@Body OwnerUserInfoUpdateRequestDTO requestDTO);





    //네이버로그인
//    @GET("/naver/callback")
//    Call<?> naverLoginCallback(@Query("code") String code, @Header("FCM-Token") String fcmToken);

    //일반 회원가입
    @POST("/join")
    Call<Void> joinUser(@Body JoinRequestDTO requestDTO);

    //일반 로그인
    @POST("/login")
    Call<LoginResultDTO> loginUser(@Body LoginRequestDTO loginRequest, @Header("FCM-Token") String fcmToken);
    
    //추가정보입력
    @PUT("/api/user/extraInfo")
    Call<Void> setUserType(@Body ExtraInfoDTO request);

    //토근 재발급
    @POST("/api/token/refresh")
    Call<Void> refreshToken(@Body RefreshTokenRequestDTO request);






    @GET("/api/area/country")
    Call<List<String>> getCountries();

    @GET("/api/area/{selectedCountry}/city")
    Call<List<String>> getCitiesByCountry(@Path("selectedCountry") String country);

    @GET("/api/crop/cropForm")
    Call<List<String>> getCropForms();

    @GET("/api/crop/{selectedCropForm}/cropType")
    Call<List<String>> getCropTypes(@Path("selectedCropForm") String cropForm);

    @GET("/api/clip")
    Call<List<ClipDTO>> getClipList();

    @HTTP(method = "DELETE", path="/api/clip", hasBody = true)
    Call<Void> deleteClip(@Body ClipDeleteRequestDTO clipDeleteRequestDTO);

    @Multipart
    @POST("/api/matching/matchingPost")
    Call<Void> matchingPost(@Part("request") RequestBody requestBody,
                            @Part MultipartBody.Part uploadImg);


    @GET("/api/matching/matchingDetail/{matchingId}")
    Call<MatchingDetailResponseDTO> getMatchingDetail(@Path("matchingId") int matchingId);

    @POST("/api/matching/matchingList")
    Call<List<MatchingListInfoDTO>> getMatchingList();

    @POST("/api/comment/matching")
    Call<List<MatchingPostCommentResponseDTO>> matchingCommentPost(@Body MatchingPostCommentRequestDTO matchingPostCommentRequestDTO);

    @GET("/api/comment/matching/{selectedMatchingId}/commentList")
    Call<List<MatchingPostCommentResponseDTO>> getMatchingCommentList(@Path("selectedMatchingId") int selectedMatchingId);

}
