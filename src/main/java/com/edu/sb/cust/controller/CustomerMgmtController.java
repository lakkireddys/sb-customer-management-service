package com.edu.sb.cust.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class CustomerMgmtController {
	
	private Counter counter;
	
	
	
	public CustomerMgmtController(MeterRegistry meterRegistry) {
		
		this.counter = Counter.builder("fetch_customer_counter")
				.tags("controller", "cusomter management")
				.description("Cusomter maanagemet service")
				.register(meterRegistry);
	}


	@GetMapping("/cust")
	public String helloCustomer() {
		counter.increment();
		return "i'm cusotmer";
	}

}
