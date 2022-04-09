package com.example.demo.model;

import lombok.Data;

@Data
public class RecipientDetails {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private RecipientAddress recipientAddress;

}
