package com.example.renthouseweb_be.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private UserDTO user;
    private HouseDTO house;
    private String content;
    private LocalDateTime createdAt;
    private int star;
}
