package com.example.deliciousfood.api.dto;

public class LoginResponseDTO {
    boolean success;
    String id;
    String password;
    String name;
    String email;

    public LoginResponseDTO(boolean success, String id, String password, String name, String email) {
        this.success = success;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
