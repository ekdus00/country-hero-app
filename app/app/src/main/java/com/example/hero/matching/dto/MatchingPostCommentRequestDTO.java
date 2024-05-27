package com.example.hero.matching.dto;

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
