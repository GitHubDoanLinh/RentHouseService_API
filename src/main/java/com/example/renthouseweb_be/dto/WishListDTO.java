package com.example.renthouseweb_be.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WishListDTO {
    private Long id;
    private UserDTO user;
    private HouseDTO house;
    private LocalDateTime createdAt;
}
