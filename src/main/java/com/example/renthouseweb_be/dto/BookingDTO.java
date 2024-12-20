package com.example.renthouseweb_be.dto;

import com.example.renthouseweb_be.model.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookingDTO {
    private LocalDateTime createAt;
    private Date startDate;
    private Date endDate;
    private Integer numberOfGuests;
    private UserDTO user;
    private HouseDTO house;
    private Double price;
    private BookingStatus status;
}
