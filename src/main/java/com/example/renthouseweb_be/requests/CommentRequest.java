package com.example.renthouseweb_be.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String content;
    private Long userId;
    private Long houseId;
    private int star;
}
