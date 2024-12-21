package com.example.renthouseweb_be.model;

import com.example.renthouseweb_be.model.account.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    private House house;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(columnDefinition = "tinyint default 0")
    private boolean deleteFlag;
}
