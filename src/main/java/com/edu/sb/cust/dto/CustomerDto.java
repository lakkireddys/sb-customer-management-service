package com.edu.sb.cust.dto;

import com.edu.sb.cust.model.CustomerDao;
import lombok.Data;

@Data
public class CustomerDto {
    private String foreName;
    private String surName;
    private String addressInfo;
    private int age;


}


