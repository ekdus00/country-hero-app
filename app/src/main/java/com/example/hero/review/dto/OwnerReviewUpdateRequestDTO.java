package com.example.hero.review.dto;

public class OwnerReviewUpdateRequestDTO {
    private String targetUserId; //구직자
    private String targetUserType; //구직자
    private Integer jobId;
    private Integer reviewo1Score;
    private Integer reviewo2Score;
    private Integer reviewo3Score;
    private Integer reviewo4Score;
    private String reviewContent;

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Integer getReviewo4Score() {
        return reviewo4Score;
    }

    public void setReviewo4Score(Integer reviewo4Score) {
        this.reviewo4Score = reviewo4Score;
    }

    public Integer getReviewo3Score() {
        return reviewo3Score;
    }

    public void setReviewo3Score(Integer reviewo3Score) {
        this.reviewo3Score = reviewo3Score;
    }

    public Integer getReviewo2Score() {
        return reviewo2Score;
    }

    public void setReviewo2Score(Integer reviewo2Score) {
        this.reviewo2Score = reviewo2Score;
    }

    public Integer getReviewo1Score() {
        return reviewo1Score;
    }

    public void setReviewo1Score(Integer reviewo1Score) {
        this.reviewo1Score = reviewo1Score;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

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
}
