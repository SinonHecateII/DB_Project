package com.example.deliciousfood.api.dto.requestDTO;

public class ReviewSearchUserIdDTO {
    String writer;

    public ReviewSearchUserIdDTO(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
