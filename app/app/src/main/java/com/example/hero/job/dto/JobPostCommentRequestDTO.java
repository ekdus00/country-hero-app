package com.example.hero.job.dto;

public class JobPostCommentRequestDTO {
    private Integer jobId;
    private String commentContent;
    private Integer commentParent;
    public JobPostCommentRequestDTO(Integer jobId, String commentContent, Integer commentParent) {
        this.jobId = jobId;
        this.commentContent = commentContent;
        this.commentParent = commentParent;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getCommentParent() {
        return commentParent;
    }

    public void setCommentParent(Integer commentParent) {
        this.commentParent = commentParent;
    }
}
