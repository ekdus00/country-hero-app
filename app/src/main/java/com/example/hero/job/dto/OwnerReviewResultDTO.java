package com.example.hero.job.dto;

import java.util.List;

public class OwnerReviewResultDTO {
    private Double totalReviewAvg;
    private Double item1ReviewAvg;
    private Double item2ReviewAvg;
    private Double item3ReviewAvg;

    private List<String> reviewContents;

    public List<String> getReviewContents() {
        return reviewContents;
    }

    public void setReviewContents(List<String> reviewContents) {
        this.reviewContents = reviewContents;
    }

    public Double getItem3ReviewAvg() {
        return item3ReviewAvg;
    }

    public void setItem3ReviewAvg(Double item3ReviewAvg) {
        this.item3ReviewAvg = item3ReviewAvg;
    }

    public Double getItem2ReviewAvg() {
        return item2ReviewAvg;
    }

    public void setItem2ReviewAvg(Double item2ReviewAvg) {
        this.item2ReviewAvg = item2ReviewAvg;
    }

    public Double getItem1ReviewAvg() {
        return item1ReviewAvg;
    }

    public void setItem1ReviewAvg(Double item1ReviewAvg) {
        this.item1ReviewAvg = item1ReviewAvg;
    }

    public Double getTotalReviewAvg() {
        return totalReviewAvg;
    }

    public void setTotalReviewAvg(Double totalReviewAvg) {
        this.totalReviewAvg = totalReviewAvg;
    }
}
