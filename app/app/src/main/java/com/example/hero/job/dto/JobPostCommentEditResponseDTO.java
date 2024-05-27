package com.example.hero.job.dto;


public class JobPostCommentEditResponseDTO {
    private String commentContent;

    // 댓글 조회 항목
    public JobPostCommentEditResponseDTO(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}

