package com.edu.sb.cust.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    private int errorCode;
    private String message;
    private String cause;
}
