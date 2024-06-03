package com.example.hero.matching.dto;

public class MatchingPostCommentRequestDTO {
    private Integer matchingId;
    private String commentContent;
    private Integer commentParent;

    public MatchingPostCommentRequestDTO() {
    }

    public Integer getMatchingId() {
        return matchingId;
    }

    public void setMatchingId(Integer matchingId) {
        this.matchingId = matchingId;
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
