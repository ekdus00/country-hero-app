package com.example.hero.etc;

import com.example.hero.clip.dto.ClipDTO;
import com.example.hero.clip.dto.ClipDeleteRequestDTO;
import com.example.hero.employer.dto.EmployResponseDTO;
import com.example.hero.employer.dto.JobPostEditResponseDTO;
import com.example.hero.employer.dto.JobRequestDTO;
import com.example.hero.employer.dto.JopPostUpdateRequestDTO;
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
import com.example.hero.job.dto.JobPostCreateRequestDTO;
import com.example.hero.job.dto.OwnerInfoResponseDTO;
import com.example.hero.job.dto.ParticipateRequestDTO;
import com.example.hero.login.dto.CheckUserRequestDTO;
import com.example.hero.login.dto.ExtraInfoDTO;
import com.example.hero.login.dto.FindUserIdRequestDTO;
import com.example.hero.login.dto.JoinRequestDTO;
import com.example.hero.login.dto.LoginRequestDTO;
import com.example.hero.login.dto.LoginResultDTO;
import com.example.hero.login.dto.NaverLoginResultDTO;
import com.example.hero.login.dto.RefreshTokenRequestDTO;
import com.example.hero.login.dto.ResetUserPwRequestDTO;
import com.example.hero.matching.dto.MatchingDetailResponseDTO;
import com.example.hero.matching.dto.MatchingListInfoDTO;
import com.example.hero.matching.dto.MatchingPostCommentDeleteRequestDTO;
import com.example.hero.matching.dto.MatchingPostCommentRequestDTO;
import com.example.hero.matching.dto.MatchingPostCommentResponseDTO;
import com.example.hero.matching.dto.MatchingPostCommentUpdateRequestDTO;
import com.example.hero.matching.dto.MentorRecommendationResponseDTO;
import com.example.hero.mypage.dto.BusinessDataDTO;
import com.example.hero.mypage.dto.BusinessNumberRequest;
import com.example.hero.mypage.dto.BusinessResponseDTO;
import com.example.hero.mypage.dto.OwnerProfileDTO;
import com.example.hero.mypage.dto.OwnerUserInfoResponseDTO;
import com.example.hero.mypage.dto.OwnerUserInfoUpdateRequestDTO;
import com.example.hero.mypage.dto.WorkerProfileDTO;
import com.example.hero.mypage.dto.WorkerUserInfoResponseDTO;
import com.example.hero.resume.dto.ResumeEditResponseDTO;
import com.example.hero.resume.dto.ResumeResponseDTO;
import com.example.hero.resume.dto.ResumeUpdateRequestDTO;
import com.example.hero.resume.dto.WorkerStateRequestDTO;
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
import retrofit2.Response;
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
import retrofit2.http.Query;


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

    //공고지원
    @POST("/api/participate")
    Call<Void> JobDetailParticipate(
            @Body ParticipateRequestDTO requestDTO
    );







    //공고댓글 조회
    @GET("/api/comment/job/{selectedJobId}/commentList")
    Call<List<JobPostCommentResponseDTO>> getJobPostCommentList(
            @Path("selectedJobId") int selectedJobId
    );

    //공고댓글 작성
    @POST("/api/comment/job")
    Call<Void> createJobPostComment(
            @Body JobPostCommentRequestDTO requestDTO
    );

//    //공고댓글 수정조회
//    @GET("/api/comment/job/{selectedJobId}/{selectedCommentId}")
//    Call<JobPostCommentEditResponseDTO> getJobPostComment(
//            @Path("selectedJobId") int selectedJobId,
//            @Path("selectedCommentId") int selectedCommentId
//    );

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




    //홈화면(구직자)
    @GET("/api/home/workerHome")
    Call<WorkerHomeDTO> getWorkerHome();

    //홈화면(구인자)
    @GET("/api/home/ownerHome")
    Call<OwnerHomeDTO> getOwnerHome();





    //지원현황
    @GET("/api/participate")
    Call<ParticipateResponseDTO> getParticipateList();

//    //지원취소
//    @DELETE("/api/participate")
//    Call<ResponseBody> DeleteParticipate(@Body ParticipateDeleteRequestDTO requestDTO);

    //지원취소
    @HTTP(method = "DELETE", path="/api/participate", hasBody = true)
    Call<ResponseBody> DeleteParticipate(@Body ParticipateDeleteRequestDTO requestDTO);

    //나의공고
    @GET("/api/employ/employList")
    Call<EmployResponseDTO> getEmployList();

    //공고현황
    @GET("/api/employ/employList/{selectedJobPost}/workerList")
    Call<List<WorkerInfoDTO>> getWorkerList(@Path("selectedJobPost") int jobPostId);

    //공고수정(조회)
    @GET("/api/job/JobPost/{selectedJobPost}/update")
    Call<JobPostEditResponseDTO> getEditJobPost(@Path("selectedJobPost") Integer selectedJobPost);

    //공고수정(수정)
    @Multipart
    @PUT("/api/job/JobPost/update")
    Call<Void> updateJobPost(@Part("request") RequestBody requestDTO, @Part MultipartBody.Part uploadImgFile);

    //공고마감
    @PUT("/api/job/JobPost/close")
    Call<Void> closeJobPost(@Body JobRequestDTO requestDTO);





    //구인자 마이페이지
    @GET("/api/user/profile/owner")
    Call<OwnerProfileDTO> getOwnerProfile();

    //구직자 마이페이지
    @GET("/api/user/profile/worker")
    Call<WorkerProfileDTO> getWorkerProfile();





    //구인자 상호평가완료/목록
    @PUT("/api/review/owner/complete")
    Call<Void> updateOwnerReview(@Body OwnerReviewUpdateRequestDTO requestDTO);

    //구직자 상호평가완료
    @PUT("/api/review/worker/complete")
    Call<Void> updateWorkerReview(@Body WorkerReviewUpdateRequestDTO requestDTO);

    //구인자 상호평가 목록
    @GET("/api/review/owner")
    Call<List<OwnerReviewInfoDTO>> getOwnerReviewList();

    //구직자 상호평가 목록
    @GET("/api/review/worker")
    Call<List<WorkerReviewInfoDTO>> getWorkerReviewList();

    //구직자 차단
    @POST("/api/block")
    Call<Void> ReviewBlock(@Body BlockRequestDTO requestDTO);



    //회원정보수정(구인자) 수정
    @PUT("/api/user/userInfo/owner")
    Call<Void> updateOwnerInfo(@Body OwnerUserInfoUpdateRequestDTO requestDTO);

    //사업자 등록 번호 인증
    @POST("api/nts-businessman/v1/status")
    Call<BusinessResponseDTO> checkBusinessStatus(@Query("serviceKey") String serviceKey, @Body BusinessNumberRequest data);

    //회원정보수정(구직자) 조회
    @GET("/api/user/userInfo/owner")
    Call<OwnerUserInfoResponseDTO> getOwnerInfo();

    //회원정보수정(구직자) 조회
    @GET("/api/user/userInfo/owner")
    Call<WorkerUserInfoResponseDTO> getWorkerInfo();




    //이력서 수정
    @Multipart
    @PUT("/api/resume")
    Call<Void> updateResume(@Part("request") ResumeUpdateRequestDTO requestDTO, @Part MultipartBody.Part uploadImgFile);

    //이력서 수정 시 조회
    @GET("/api/resume")
    Call<ResumeEditResponseDTO> getResume();

    //구인자의 이력서 확인
    @GET("/api/resume/{selectedWorker}")
    Call<ResumeResponseDTO> checkResume(@Path("selectedUserId") String selectedUserId);

    //일자리 승인
    @PUT("/api/resume/approve")
    Call<ResponseBody> updateApprove(@Body WorkerStateRequestDTO requestDTO);

    //일자리 보류
    @PUT("/api/resume/defer")
    Call<ResponseBody> updateDefer(@Body WorkerStateRequestDTO requestDTO);




    //네이버로그인
    @GET("/naver/callback")
    Call<NaverLoginResultDTO> naverLoginCallback(@Header("FCM-Token") String fcmToken);

    //일반 회원가입
    @POST("/join")
    Call<Void> joinUser(@Body JoinRequestDTO requestDTO);

    //일반 로그인
    @POST("/login")
    Call<LoginResultDTO> loginUser(@Body LoginRequestDTO loginRequest);
    
    //추가정보입력
    @PUT("/api/user/extraInfo")
    Call<Void> setUserType(@Body ExtraInfoDTO request);

    // 아이디 찾기
    @POST("/api/user/retrieve/id")
    Call<String> findUserId(@Body FindUserIdRequestDTO request);

    // 사용자 인증
    @POST("/api/user/check")
    Call<Response<String>> checkUser(@Body CheckUserRequestDTO request);

    // 비밀번호 재설정
    @POST("/api/user/reset/pw")
    Call<Void> resetUserPw(@Body ResetUserPwRequestDTO request);

    //토근 재발급
    @POST("/api/token/refresh")
    Call<Void> refreshToken(@Body RefreshTokenRequestDTO request);






    //시/도 조회
    @GET("/api/area/country")
    Call<List<String>> getCountries();

    //시/군/구 조회
    @GET("/api/area/{selectedCountry}/city")
    Call<List<String>> getCitiesByCountry(@Path("selectedCountry") String country);

    //품목 조회
    @GET("/api/crop/cropForm")
    Call<List<String>> getCropForms();

    //작물조회
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

    @PUT("/api/comment/matching")
    Call<List<MatchingPostCommentResponseDTO>> matchingPostCommentUpdate( @Body MatchingPostCommentUpdateRequestDTO matchingPostCommentUpdateRequestDTO);

    @DELETE("/api/comment/matching")
    Call<List<MatchingPostCommentResponseDTO>> matchingPostCommentDelete(@Body MatchingPostCommentDeleteRequestDTO matchingPostCommentDeleteRequestDTO);









    //멘토 추천 리스트 조회
    @GET("/api/matching/mentorRecommendation")
    Call<List<MentorRecommendationResponseDTO>> getMatchingRecom();

    //멘토 추천 리스트 선택 글 조회
    @GET("/api/matching/{selectedMentorId}/mentorMatchingPostList")
    Call<List<MatchingListInfoDTO>> getMatchingRecomList(@Path("selectedMentorId") String selectedMentorId);

    //회원탈퇴
    @DELETE("/api/user/withdrawal")
    Call<Void> withdrawalUser();

    //푸시알림 승인
    @POST("/api/fcm/approve")
    Call<Void> approveFCM(@Header("FCM-Token") String fcmToken);

    //푸시알리 거절
    @DELETE("/api/fcm/refuse")
    Call<Void> refuseFCM(@Header("FCM-Token") String fcmToken);

}
