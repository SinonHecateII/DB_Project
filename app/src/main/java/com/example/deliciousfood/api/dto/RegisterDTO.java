package com.example.deliciousfood.api.dto;

import com.google.gson.annotations.SerializedName;

public class RegisterDTO {
    @SerializedName("id")
    private String id;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;

    public RegisterDTO(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
