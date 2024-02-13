package com.app.account.service;

import org.springframework.http.ResponseEntity;

import com.app.account.model.CustomerEntityRequest;
import com.app.account.model.TransactionRequest;

public interface AccountService {
	
	ResponseEntity<String> createAccount(CustomerEntityRequest customerRequest);
	
	ResponseEntity<?> transaction(TransactionRequest request);
	
	ResponseEntity<?> getAccountDetails(Integer custId, String accountNumber);
	
	ResponseEntity<?> deleteAccount(Integer custId, String accountNumber);

	ResponseEntity<?> deleteAccountFromCustomer(String accountNumber);

	ResponseEntity<?> getAccountDetailsForDelete(String accountNumber);
	

}
