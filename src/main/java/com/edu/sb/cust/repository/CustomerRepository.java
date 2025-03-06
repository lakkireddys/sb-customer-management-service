package com.edu.sb.cust.repository;

import com.edu.sb.cust.model.CustomerDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerDao, String> {

    public CustomerDao findByForeName(String name );
    public List<CustomerDao> findByAddressInfo(String address);
}
