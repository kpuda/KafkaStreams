package com.kp.kafka.model.incident;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Incident {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String street;
    private String number;
    private String apartamentNumber;
}
