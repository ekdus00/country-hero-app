package com.example.hero.job.dto;

import java.time.LocalDateTime;
import java.util.List;

public class JobPostCommentResponseDTO {

    private String commentContent;
    private String userName;
    private LocalDateTime updatedCommentDatetime;
    private Integer commentId;
    private List<JobPostCommentResponseDTO> childCommentList;

    // 댓글 조회 항목
    public JobPostCommentResponseDTO(Integer commentId, String commentContent, String userName, LocalDateTime updatedCommentDateTime) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.userName = userName;
        this.updatedCommentDatetime = updatedCommentDateTime;
    }


    // 부모 댓글 여부를 확인하는 메서드
    public boolean isParent() {
        return (this.childCommentList == null || this.childCommentList.isEmpty());
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getUpdatedCommentDatetime() {
        return updatedCommentDatetime;
    }

    public void setUpdatedCommentDatetime(LocalDateTime updatedCommentDatetime) {
        this.updatedCommentDatetime = updatedCommentDatetime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public List<JobPostCommentResponseDTO> getChildCommentList() {
        return childCommentList;
    }

    public void setChildCommentList(List<JobPostCommentResponseDTO> childCommentList) {
        this.childCommentList = childCommentList;
    }

}
