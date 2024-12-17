package com.example.renthouseweb_be.requests;

import com.example.renthouseweb_be.model.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookingRequest {
    private BookingStatus status;
    private Date startDate;
    private Date createAt;
    private Integer numberOfGuests;
    private Long userId;
    private Long houseId;
    private Double price;
}
