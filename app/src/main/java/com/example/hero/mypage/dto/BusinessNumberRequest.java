package com.example.hero.mypage.dto;

import java.util.List;

public class BusinessNumberRequest {
    private List<String> b_no;

    public BusinessNumberRequest(List<String> b_no) {
        this.b_no = b_no;
    }

    public List<String> getB_no() {
        return b_no;
    }

    public void setB_no(List<String> b_no) {
        this.b_no = b_no;
    }
}
