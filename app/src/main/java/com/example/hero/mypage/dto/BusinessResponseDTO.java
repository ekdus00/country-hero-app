package com.example.hero.mypage.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessResponseDTO {
    @SerializedName("request_cnt")
    private int requestCount;

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("data")
    private List<BusinessDataDTO> data;

    public List<BusinessDataDTO> getData() {
        return data;
    }

    public void setData(List<BusinessDataDTO> data) {
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }
}
