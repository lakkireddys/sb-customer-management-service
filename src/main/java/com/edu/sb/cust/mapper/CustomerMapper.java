package com.edu.sb.cust.mapper;

import com.edu.sb.cust.dto.CustomerDto;
import com.edu.sb.cust.model.CustomerDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDao convertToDao(CustomerDto customerDto);


}
