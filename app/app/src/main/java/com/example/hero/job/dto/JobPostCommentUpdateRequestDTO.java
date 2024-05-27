package com.example.hero.job.dto;



public class JobPostCommentUpdateRequestDTO {
    private Integer jobId;
    private Integer commentId;
    private String commentContent;

    public JobPostCommentUpdateRequestDTO() {

    }

    // Getters
    public Integer getJobId() {
        return jobId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    // Setters
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}

