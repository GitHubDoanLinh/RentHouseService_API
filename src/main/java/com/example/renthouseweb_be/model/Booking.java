package com.example.renthouseweb_be.model;

import com.example.renthouseweb_be.model.account.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
    @Column
    private Date startDate;
    @Column
    private Date createAt;
    @Column
    private Date endDate;
    @Column(name = "number_of_guests")
    private Integer numberOfGuests;
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    private House house;
    private Double price;
    @Column(columnDefinition = "tinyint default 0")
    private boolean deleteFlag;
}
