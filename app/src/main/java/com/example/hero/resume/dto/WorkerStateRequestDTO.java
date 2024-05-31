package com.example.hero.resume.dto;

public class WorkerStateRequestDTO {
    private Integer jobId;
    private String targetUserId; //구직자 ID
    private String targetUserType; //구직자 Type

    public String getTargetUserType() {
        return targetUserType;
    }

    public void setTargetUserType(String targetUserType) {
        this.targetUserType = targetUserType;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
}