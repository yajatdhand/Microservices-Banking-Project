package com.customer.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.app.dto.CustomerAddRequest;
import com.customer.app.dto.CustomerEntityRequest;
import com.customer.app.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {

	@Autowired
	CustomerService service;

	@PostMapping("/addCustomer")
	ResponseEntity<?> addCustomer(@RequestBody CustomerAddRequest request) {
		return service.addCustomer(request);
	}

	@PutMapping("/updateCustomer")
	ResponseEntity<?> updateCustomer(@RequestBody CustomerEntityRequest request) {
		return service.updateCustomer(request);
	}

	@GetMapping("/deleteCustomer")
	ResponseEntity<?> deleteCustomer(@Param("custId") Integer custId,
			@Param("custAccountNumber") String custAccountNumber) {
		return service.deleteCustomer(custId, custAccountNumber);
	}

	@GetMapping("/getCustomer")
	ResponseEntity<?> getCustomer(@Param("custId") Integer custId) {
		return service.getCustomer(custId);
	}

	@GetMapping("/getAllCustomers")
	ResponseEntity<?> getAllCustomers() {
		return service.getAllCustomers();
	}

	@GetMapping("/deleteCustomerFromAccount")
	ResponseEntity<?> deleteCustomerFromAccount(@Param("custId") Integer custId) {
		return service.deleteCustomerFromAccount(custId);
	}

}
