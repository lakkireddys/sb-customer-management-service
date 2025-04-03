package com.edu.sb.cust.service;

import com.edu.sb.cust.dto.CustomerDto;

import java.util.List;

public interface CustomerManagementService {
    public int createCustomer(CustomerDto customerDto);
    public CustomerDto findCustomer(String name);
    public List<CustomerDto> findAllCustomerByAddress(String address);
    public int createCustomerWithAddress(CustomerDto customerDto);
}
