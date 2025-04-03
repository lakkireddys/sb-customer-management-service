package com.edu.sb.cust.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private Integer addressId;
    private String addressLine1;
    private String addressLine2;
    private String country;
    private String postCode;
}
