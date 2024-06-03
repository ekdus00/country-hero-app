package com.example.hero.matching.dto;

public class MatchingPostCommentDeleteRequestDTO {
    private Integer matchingId;
    private Integer commentId;

    public MatchingPostCommentDeleteRequestDTO() {
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
}
