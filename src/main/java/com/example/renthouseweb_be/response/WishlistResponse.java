package com.example.renthouseweb_be.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponse {
    private Long id;
    private Long houseId;
    private Long userId;
    private String name;
    private String description;
    private double price;
    private String location;
    private String categoryName;
    private String hostName;
    private LocalDateTime createdAt;
}
