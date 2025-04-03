package com.edu.sb.cust.service;

import com.edu.sb.cust.dto.Address;
import com.edu.sb.cust.dto.CustomerDto;
import com.edu.sb.cust.exception.MatchingAddressNotFound;
import com.edu.sb.cust.mapper.CustomerMapperImpl;
import com.edu.sb.cust.model.CustomerDao;
import com.edu.sb.cust.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerManagementServiceImpl implements CustomerManagementService{

    Logger log = LogManager.getLogger(CustomerManagementServiceImpl.class);
    private static final Marker HAI = MarkerManager.getMarker("lakki");
    private final CustomerRepository repository;
    private final RestClient restClient;
    private final WebClient webClient;
    private final CustomerMapperImpl mapper;

    @Override
    public int createCustomer(CustomerDto customerDto) {
        log.info( HAI, "process the customer after converting to dao object...{} ", customerDto.getAddressInfo().getAddressLine1());
        Address address = customerDto.getAddressInfo();
        int addressID ;
        try {
            Address resultAddress= getAddress(address);
            if(ObjectUtils.isEmpty(resultAddress))
                throw new MatchingAddressNotFound(HttpStatusCode.valueOf(500),"Matching address is null");
            addressID = resultAddress.getAddressId();
            log.info("existing address... {}", addressID );
            CustomerDao customer = new CustomerDao();
            customer.setForeName(customerDto.getForeName());
            customer.setSurName(customerDto.getSurName());
            customer.setAge(customerDto.getAge());
            customer.setAddressId(addressID);
            repository.insert(customer);
        } catch (MatchingAddressNotFound e) {
            log.error("error msg {}", e.getMsg());
            throw new RuntimeException(e);
        }
        return addressID;
    }

    private Address getAddress(Address address) throws MatchingAddressNotFound{
        Address matchingAddress ;
        try {
            matchingAddress = restClient
                .get()
                .uri(uriBuilder ->
                    uriBuilder
                        .path("get-address-by-lines")
                        .queryParam("line1", address.getAddressLine1())
                        .queryParam("line2", address.getAddressLine2()).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) ->  {
                    throw new MatchingAddressNotFound(HttpStatusCode.valueOf(404), "On Status noMatchFound");
                })
                .toEntity(Address.class).getBody();
             if (ObjectUtils.isEmpty(matchingAddress)){
                matchingAddress = restClient
                         .post()
                         .uri("add-address")
                         .body(address)
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON)
                         .retrieve()
                         .toEntity(Address.class).getBody();
             }

        } catch (Exception e) {
            log.error("failed to find matching address {}", e.getMessage());
//            return ResponseEntity.notFound().build();
            throw new MatchingAddressNotFound(HttpStatusCode.valueOf(404), "noMatchFound");
        }
        return matchingAddress;
    }


    @Override
    public CustomerDto findCustomer(String name) {
        CustomerDao customerDao = repository.findByForeName(name);
        return ObjectUtils.isEmpty(customerDao) ? null : getCustomerDto(customerDao);
    }

    private CustomerDto getCustomerDto(CustomerDao customerDao) {
        CustomerDto customerFound = new CustomerDto();
        customerFound.setForeName(customerDao.getForeName());
        customerFound.setSurName(customerDao.getSurName());
        customerFound.setAge(customerDao.getAge());
        return customerFound;
    }

    @Override
    public List<CustomerDto> findAllCustomerByAddress(String address) {
        log.info("finding all the customers based on address {}", address);
        List<CustomerDto> customerList = new ArrayList<>();
        for (CustomerDao customerDao : repository.findAll()) {
            customerList.add(getCustomerDto(customerDao));
        }
        return customerList;
    }

    @Override
    public int createCustomerWithAddress(CustomerDto customerDto){

        int newAddressId = getAddressViaWebClient(customerDto.getAddressInfo());
        log.info("new Address Line is {}", newAddressId);
        if(newAddressId == 0)
            throw new MatchingAddressNotFound(HttpStatusCode.valueOf(500), "Error while creating new address record");
        CustomerDao customerDao = mapper.convertToDao(customerDto);
        customerDao.setAddressId(newAddressId);
        repository.save(customerDao);
        return newAddressId;

    }

    private int getAddressViaWebClient(Address address) {
        Address newAddress = webClient.post()
                .uri("create-customer-with-address")
                .body(BodyInserters.fromValue(address))
                .retrieve()
                .bodyToMono(Address.class)
                .block();
        return newAddress != null ? newAddress.getAddressId() : 0;
    }
}
