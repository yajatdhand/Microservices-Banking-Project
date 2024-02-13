package com.app.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.account.model.CustomerEntityRequest;
import com.app.account.model.TransactionRequest;
import com.app.account.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService service;

	@PostMapping("/createAccount")
	ResponseEntity<String> createAccount(@RequestBody CustomerEntityRequest customerRequest) {
		return service.createAccount(customerRequest);
	}

	@PostMapping("/transaction")
	ResponseEntity<?> transaction(@RequestBody TransactionRequest request) {
		return service.transaction(request);
	}

	@GetMapping("/getAccountDetails")
	ResponseEntity<?> getAccountDetails(@Param("custId") Integer custId, @Param("accountNumber") String accountNumber) {
		return service.getAccountDetails(custId, accountNumber);
	}

	@GetMapping("/deleteAccount")
	ResponseEntity<?> deleteAccount(@Param("custId") Integer custId, @Param("accountNumber") String accountNumber) {
		return service.deleteAccount(custId, accountNumber);
	}
	
	@GetMapping("/getAccountDetailsForDelete")
	ResponseEntity<?> getAccountDetailsForDelete(@Param("accountNumber") String accountNumber) {
		return service.getAccountDetailsForDelete(accountNumber);
	}

	@GetMapping("/deleteAccountFromCustomer")
	ResponseEntity<?> deleteAccountFromCustomer(@Param("accountNumber") String accountNumber) {
		return service.deleteAccountFromCustomer(accountNumber);
	}
}
