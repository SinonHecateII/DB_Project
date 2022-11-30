package com.example.deliciousfood.api.dto.responseDTO;

import com.google.gson.annotations.SerializedName;

public class OnlyResultDTO {
    @SerializedName("result")
    String result;

    public OnlyResultDTO(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
