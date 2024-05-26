package com.example.hero_home.model;

public class MatchingPostCommentRequestDTO {
    private Integer matchingId;
    private String commentContent;
    private Integer commentParent;

    public MatchingPostCommentRequestDTO(Integer matchingId, String commentContent, Integer commentParent) {
        this.matchingId = matchingId;
        this.commentContent = commentContent;
        this.commentParent = commentParent;
    }
}
