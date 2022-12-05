package com.example.deliciousfood.api.dto.requestDTO;

import com.google.gson.annotations.SerializedName;

public class DeleteAccountDTO {
    @SerializedName("id")
    private String id;

    public DeleteAccountDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
