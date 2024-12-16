package com.example.renthouseweb_be.requests;

import com.example.renthouseweb_be.model.Category;
import com.example.renthouseweb_be.model.account.User;
import lombok.Data;
import java.util.Set;
import java.util.List;

@Data
public class CreateHouseRequest {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String location;
    private int bedRoom;
    private int bathRoom;
    private int livingRoom;
    private int kitchen;
    private User user;
    private Category category;
    private List<String> images;
    private List<Long> convenientIds;
}
