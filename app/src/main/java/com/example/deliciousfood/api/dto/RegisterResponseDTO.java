package com.example.deliciousfood.api.dto;

public class RegisterResponseDTO {
    String result;

    public RegisterResponseDTO(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
