package com.edu.sb.cust.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchingAddressNotFound extends RuntimeException{
    private HttpStatusCode statusCode;
    private String msg;


}
