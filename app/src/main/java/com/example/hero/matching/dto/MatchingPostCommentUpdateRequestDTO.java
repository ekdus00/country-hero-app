package com.example.hero.matching.dto;

public class MatchingPostCommentUpdateRequestDTO {
    private Integer matchingId;
    private Integer commentId;
    private String commentContent;

    public MatchingPostCommentUpdateRequestDTO() {
    }

    public Integer getMatchingId() {
        return matchingId;
    }

    public void setMatchingId(Integer matchingId) {
        this.matchingId = matchingId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
