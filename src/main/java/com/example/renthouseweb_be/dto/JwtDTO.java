package com.example.renthouseweb_be.dto;

import lombok.Data;

@Data
public class JwtDTO {
    private Long id;
    private String accessToken;
}
