package com.example.renthouseweb_be.requests;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {
    private String name;
    private String location;
    private Long categoryId;
    private double minPrice;
    private double maxPrice;
    private List<Long> convenientIds;
}
