package com.example.hero.matching.dto;

import okhttp3.MultipartBody;

public class MatchingPostUpdateRequestDTO {
    private Integer matchingId;
    private String matchingName;
    private String country;
    private String city;
    private String startEduDate;
    private String endEduDate;
    private String snsUrl;
    private String eduContent;
    private MultipartBody.Part uploadImgFile; //이미지 파일

    public MatchingPostUpdateRequestDTO(Integer matchingId, String matchingName, String country, String city, String startEduDate, String endEduDate, String snsUrl, String eduContent) {
        this.matchingId = matchingId;
        this.matchingName = matchingName;
        this.country = country;
        this.city = city;
        this.startEduDate = startEduDate;
        this.endEduDate = endEduDate;
        this.snsUrl = snsUrl;
        this.eduContent = eduContent;
    }
}
