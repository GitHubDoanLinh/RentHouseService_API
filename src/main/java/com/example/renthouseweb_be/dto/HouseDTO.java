package com.example.renthouseweb_be.dto;

import com.example.renthouseweb_be.model.Category;
import com.example.renthouseweb_be.model.Convenient;
import lombok.Data;

import java.util.Set;

@Data
public class HouseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String location;
    private Category category;
    private Set<Convenient> convenients;
    private  UserDTO userDTO;
}
