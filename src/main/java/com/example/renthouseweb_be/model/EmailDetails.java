package com.example.renthouseweb_be.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    //Class data members
    private String recipient;
    private String attachment;
}
