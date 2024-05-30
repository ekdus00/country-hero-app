package com.example.hero.mypage.dto;

import com.google.gson.annotations.SerializedName;

public class BusinessDataDTO {
    @SerializedName("b_no")
    private String businessNumber;

    @SerializedName("b_stt_cd")
    private String businessStatusCode;

    public String getBusinessStatusCode() {
        return businessStatusCode;
    }

    public void setBusinessStatusCode(String businessStatusCode) {
        this.businessStatusCode = businessStatusCode;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }
}
