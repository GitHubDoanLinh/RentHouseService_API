package com.example.renthouseweb_be.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String fullName;
    private String address;
    private String phone;
    private int age;
    private String dateOfBirth;
    private String imageUser;
}
