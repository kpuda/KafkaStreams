package com.example.demo.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Incident {
    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String street;
    private String number;
    private String apartamentNumber;
}
