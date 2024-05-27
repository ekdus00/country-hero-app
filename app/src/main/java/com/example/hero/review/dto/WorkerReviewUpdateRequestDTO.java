package com.example.hero.review.dto;

public class WorkerReviewUpdateRequestDTO {
    private String targetUserId; //구인자
    private String targetUserType; //구인자
    private Integer jobId;
    private Integer review1Score;
    private Integer review2Score;
    private Integer review3Score;
    private String reviewContent;

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Integer getReview3Score() {
        return review3Score;
    }

    public void setReview3Score(Integer review3Score) {
        this.review3Score = review3Score;
    }

    public Integer getReview2Score() {
        return review2Score;
    }

    public void setReview2Score(Integer review2Score) {
        this.review2Score = review2Score;
    }

    public Integer getReview1Score() {
        return review1Score;
    }

    public void setReview1Score(Integer review1Score) {
        this.review1Score = review1Score;
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
