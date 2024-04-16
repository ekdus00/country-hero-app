package com.example.hero;

public class MentorMenteeData {

    private String status;
    private String content;
    private String date;
    private String name;

    public MentorMenteeData(String status, String content, String date, String name) {
        this.status = status;
        this.content = content;
        this.date = date;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String area) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String item) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
