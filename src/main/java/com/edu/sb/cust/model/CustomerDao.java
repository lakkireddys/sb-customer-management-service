package com.edu.sb.cust.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("customer")
@Data
public class CustomerDao {

    private String foreName;
    private String surName;
    private String addressInfo;
    private int age;
    @CreatedDate
    private LocalDate createdTime;

}
