package com.example.renthouseweb_be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Convenient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column(columnDefinition = "tinyint default 0")
    private boolean deleteFlag;
}
