package com.edu.sb.cust.controller;

import com.edu.sb.cust.dto.CustomerDto;
import com.edu.sb.cust.dto.ErrorDto;
import com.edu.sb.cust.service.CustomerManagementService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerMgmtController {
	private final CustomerManagementService customerManagementService;
	Logger log = LogManager.getLogger(CustomerMgmtController.class);
	@PostMapping("/add-customer")
	public ResponseEntity<CustomerDto> processCustomer(@RequestBody CustomerDto customerDto) {
		log.info("process customer {}", customerDto.getAddressInfo());
		customerManagementService.createCustomer(customerDto);
		return ResponseEntity.ok(customerDto);
	}

	@GetMapping("fetch-customer")
	public ResponseEntity<?> findCustomerByName(@RequestParam String name){
		log.info("finding customer details for the name {}", name);
		CustomerDto customer = customerManagementService.findCustomer(name);
	//	if((customer.getForeName()==null)) { // this is validate based on fields
		if(ObjectUtils.isEmpty(customer)){ // based on object
			log.error("no matching record found");
			return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ErrorDto(404, "No Matching Found for the name "+name, "n/a"));
//			return ResponseEntity.notFound().build();
		}
		else
			return ResponseEntity.ok(customer);
	}
	@GetMapping("find-customers-by-address")
	public ResponseEntity<List<CustomerDto>> findCustomers(@RequestParam String address){
		log.info("fetching customer list based on address {}", address);
		List<CustomerDto> allCustomerByAddress = customerManagementService.findAllCustomerByAddress(address);
		return ResponseEntity.ok(allCustomerByAddress);
	}

}
