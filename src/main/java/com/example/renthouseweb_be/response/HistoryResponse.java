package com.example.renthouseweb_be.response;

import com.example.renthouseweb_be.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    private Long bookingId;
    private Long idHost;
    private Long houseId;
    private String categoryName;
    private Double price;
    private String name;
    private String location;
    private LocalDateTime createAt;
    private String hostName;
    private Date startDate;
    private Date endDate;
    private Integer numberOfGuests;
    private BookingStatus status;
    private Boolean commented;
}
