package com.example.hero.matching.dto;

import java.util.List;

public class MatchingPostCommentResponseDTO {
    private String commentContent;
    private String userName;
    private String updatedCommentDatetime;
    private Integer commentId;
    private List<MatchingPostCommentResponseDTO> childCommentList;

    // 댓글 조회 항목
    public MatchingPostCommentResponseDTO(Integer commentId, String commentContent, String userName, String updatedCommentDateTime) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.userName = userName;
        this.updatedCommentDatetime = updatedCommentDateTime;
    }

    public void setChildCommentList(List<MatchingPostCommentResponseDTO> childCommentList) {
        this.childCommentList = childCommentList;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setUpdatedCommentDatetime(String updatedCommentDatetime) {
        this.updatedCommentDatetime = updatedCommentDatetime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    // 부모 댓글 여부를 확인하는 메서드
    public boolean isParent() {
        return (this.childCommentList == null || this.childCommentList.isEmpty());
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getUserName() {
        return userName;
    }

    public String getUpdatedCommentDatetime() {
        return updatedCommentDatetime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public List<MatchingPostCommentResponseDTO> getChildCommentList() {
        return childCommentList;
    }
}
