package com.edu.sb.cust.service;

import com.edu.sb.cust.dto.CustomerDto;
import com.edu.sb.cust.model.CustomerDao;
import com.edu.sb.cust.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerManagementServiceImpl implements CustomerManagementService{

    Logger log = LogManager.getLogger(CustomerManagementServiceImpl.class);
    private final CustomerRepository repository;
    @Override
    public void createCustomer(CustomerDto customerDto) {
        log.info("process the customer after converting to dao object...");
        CustomerDao customer = new CustomerDao();
        customer.setForeName(customerDto.getForeName());
        customer.setSurName(customerDto.getSurName());
        customer.setAge(customerDto.getAge());
        customer.setAddressInfo(customerDto.getAddressInfo());
//        customer.setCreatedTime(LocalDate.now());
        repository.insert(customer);

    }

    @Override
    public CustomerDto findCustomer(String name) {
        CustomerDao customerDao = repository.findByForeName(name);
        return ObjectUtils.isEmpty(customerDao) ? null : getCustomerDto(customerDao);
    }

    private CustomerDto getCustomerDto(CustomerDao customerDao) {
        CustomerDto customerFound = new CustomerDto();
//        CustomerDto emptyCustomer;
        customerFound.setForeName(customerDao.getForeName());
        customerFound.setSurName(customerDao.getSurName());
        customerFound.setAddressInfo(customerDao.getAddressInfo());
        customerFound.setAge(customerDao.getAge());
        return customerFound;
    }

    @Override
    public List<CustomerDto> findAllCustomerByAddress(String address) {
        log.info("finding all the customers based on address {}", address);
        List<CustomerDto> customerList = new ArrayList<>();
        for (CustomerDao customerDao : repository.findByAddressInfo(address)) {
            customerList.add(getCustomerDto(customerDao));
        }
        return customerList;
    }
}
