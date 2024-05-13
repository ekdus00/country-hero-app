package com.example.hero.dto;

public class JobDetailRequestDTO {
    private Integer jobId;
    private String userId;
    private String userType;

    public JobDetailRequestDTO(Integer jobId, String userId, String userType) {
        this.jobId = jobId;
        this.userId = userId;
        this.userType = userType;

    }
    public JobDetailRequestDTO() {
        // 기본 생성자
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
