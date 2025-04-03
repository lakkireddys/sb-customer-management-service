package com.edu.sb.cust.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private String foreName;
    private String surName;
    private Address addressInfo;
    private int age;
}


