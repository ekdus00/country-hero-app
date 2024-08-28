package com.example.hero.review.dto;

import java.time.LocalDate;

public class OwnerReviewInfoDTO {
    private String country;
    private String city;
    private String cropForm;
    private String cropType;
    private String jobName;
    private String startWorkDate;
    private String endWorkDate;
    private String workerName;
    private Integer reviewId;
    private Integer jobId;
    private String targetUserId;

    public OwnerReviewInfoDTO(String country, String city, String cropForm, String cropType, String jobName, String startWorkDate, String endWorkDate, String workerName) {
        this.jobName = jobName;
        this.startWorkDate = startWorkDate;
        this.endWorkDate = endWorkDate;
        this.country = country;
        this.city = city;
        this.cropForm = cropForm;
        this.cropType = cropType;
        this.workerName = workerName;
//        this.reviewId = reviewId;
//        this.jobId = jobId;
//        this.targetUserId = targetUserId;
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

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(String endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public String getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getCropForm() {
        return cropForm;
    }

    public void setCropForm(String cropForm) {
        this.cropForm = cropForm;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
