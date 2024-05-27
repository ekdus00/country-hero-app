package com.example.hero.employer.dto;

public class WorkerInfoDTO {
    private Integer jobId;
    private String userId;
    private String userName;
    private Integer userLevel;
    private String approval;

    public WorkerInfoDTO(Integer jobId, String userId, String userName, Integer userLevel, String approval) {
        this.jobId = jobId;
        this.userId = userId;
        this.userName = userName;
        this.userLevel = userLevel;
        this.approval = approval;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

}
