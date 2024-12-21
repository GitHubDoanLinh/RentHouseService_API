package com.example.renthouseweb_be.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishListRequest {
    private Long userId;
    private Long houseId;
}
