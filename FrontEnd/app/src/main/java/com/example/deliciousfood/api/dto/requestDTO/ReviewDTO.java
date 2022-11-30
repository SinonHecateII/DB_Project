package com.example.deliciousfood.api.dto.requestDTO;

public class ReviewDTO {
    String content;
    int score;
    int restaurantID;
    String writer;
    long createdAt;

    public ReviewDTO(String content, int score, int restaurantID, String writer, long createdAt) {
        this.content = content;
        this.score = score;
        this.restaurantID = restaurantID;
        this.writer = writer;
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
