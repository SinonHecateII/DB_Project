package com.example.deliciousfood.api.dto.requestDTO;

import com.google.gson.annotations.SerializedName;

public class LoginDTO {
    @SerializedName("id")
    private String id;
    @SerializedName("password")
    private String password;

    public LoginDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
