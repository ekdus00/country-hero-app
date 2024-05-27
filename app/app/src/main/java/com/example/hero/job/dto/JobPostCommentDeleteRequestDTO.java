package com.example.hero.job.dto;

public class JobPostCommentDeleteRequestDTO {
    private Integer jobId;
    private Integer commentId;

    public JobPostCommentDeleteRequestDTO() {
    }

    // Getters
    public Integer getJobId() {
        return jobId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    // Setters
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
}

