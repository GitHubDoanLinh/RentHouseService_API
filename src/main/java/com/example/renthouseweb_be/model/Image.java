package com.example.renthouseweb_be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String image;
    @ManyToOne
    private House house;
    @Column(columnDefinition = "tinyint default 0")
    private boolean deleteFlag;
}
