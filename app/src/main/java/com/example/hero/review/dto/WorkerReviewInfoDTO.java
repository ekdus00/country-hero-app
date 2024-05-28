package com.example.hero.review.dto;

import java.time.LocalDate;

public class WorkerReviewInfoDTO {
    private String country;
    private String city;
    private String cropForm;
    private String cropType;
    private String jobName;
    private LocalDate startWorkDate;
    private LocalDate endWorkDate;
    private String ownerName;
    private Integer reviewId;
    private Integer jobId;
    private String targetUserId;

    public WorkerReviewInfoDTO(String country, String city, String cropForm, String cropType, String jobName, LocalDate startWorkDate, LocalDate endWorkDate) {
        this.jobName = jobName;
        this.startWorkDate = startWorkDate;
        this.endWorkDate = endWorkDate;
        this.country = country;
        this.city = city;
        this.cropForm = cropForm;
        this.cropType = cropType;
//        this.ownerName = ownerName;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public LocalDate getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(LocalDate endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public LocalDate getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(LocalDate startWorkDate) {
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