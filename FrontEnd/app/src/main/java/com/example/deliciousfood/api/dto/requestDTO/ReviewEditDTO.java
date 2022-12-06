package com.example.deliciousfood.api.dto.requestDTO;

public class ReviewEditDTO {
    int reviewID;
    int score;
    String content;

    public ReviewEditDTO(int reviewID, int score, String content) {
        this.reviewID = reviewID;
        this.score = score;
        this.content = content;
    }
}
