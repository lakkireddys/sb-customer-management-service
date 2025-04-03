package com.edu.sb.cust.controller;

import com.edu.sb.cust.dto.CustomerDto;
import com.edu.sb.cust.dto.ErrorDto;
import com.edu.sb.cust.enums.ClientName;
import com.edu.sb.cust.service.CustomerManagementService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerMgmtController {
	private  final CustomerManagementService customerManagementService;
	Logger log = LogManager.getLogger(CustomerMgmtController.class);
	@PostMapping("/add-customer")
	public ResponseEntity<?> processCustomer(@RequestBody CustomerDto customerDto, @RequestParam("clientType") String clientType) {
		log.info("process customer {}", customerDto.getAddressInfo());
		log.log(Level.INFO, "hai");
		
		int addressId;
        try {
			if (clientType.equals(ClientName.WEBCLIENT.name())){
				log.info("processing via webClient");
				addressId = customerManagementService.createCustomerWithAddress(customerDto);
			} else {
				log.info("processing via restClient");
            	addressId = customerManagementService.createCustomer(customerDto);
			}
        } catch (Exception e) {
			log.error("Controller error {}", e.getMessage());
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(new ErrorDto(404, e.getMessage(),""));
        }
        customerDto.getAddressInfo().setAddressId(addressId);
		return ResponseEntity.ok(customerDto);
	}

	@GetMapping("fetch-customer")
	public ResponseEntity<?> findCustomerByName(@RequestParam String name){
		log.info("finding customer details for the name {}", name);
		CustomerDto customer = customerManagementService.findCustomer(name);
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

	@GetMapping("test-path-param-behaviour/{pathName}/{pathVariable}")
	public String retrieveInfoPathParamBased(@PathVariable String pathName, @PathVariable String pathVariable) {
		return "hai ".concat(pathName).concat(pathVariable).concat(" there");
	}

}
