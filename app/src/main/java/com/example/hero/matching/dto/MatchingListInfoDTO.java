package com.example.hero.matching.dto;

public class MatchingListInfoDTO {
    private Integer matchingId;
    private String matchingName;
    private String writerType;
    private String createdMatchingDate;
    private String userName; //매칭글 작성자 이름

    public MatchingListInfoDTO(Integer matchingId, String matchingName, String writerType, String createdMatchingDate, String userName) {
        this.matchingId = matchingId;
        this.matchingName = matchingName;
        this.writerType = writerType;
        this.createdMatchingDate = createdMatchingDate;
        this.userName = userName;
    }

    public Integer getMatchingId() {
        return matchingId;
    }

    public String getMatchingName() {
        return matchingName;
    }

    public String getWriterType() {
        return writerType;
    }

    public String getCreatedMatchingDate() {
        return createdMatchingDate;
    }

    public String getUserName() {
        return userName;
    }
}
