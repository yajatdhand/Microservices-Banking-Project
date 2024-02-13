package com.app.account.service.impl;

import java.util.Date;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.account.constants.StringConstants;
import com.app.account.entity.AccountEntity;
import com.app.account.model.CustomerEntityRequest;
import com.app.account.model.CustomerModel;
import com.app.account.model.TransactionRequest;
import com.app.account.repository.AccountRepository;
import com.app.account.service.AccountService;
import com.app.account.utils.AccountUtils;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository repository;

	@Autowired
	AccountUtils utils;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	RestTemplate restTemplate;

	public CustomerEntityRequest checkIfCustomerExists(Integer custId) {
		ResponseEntity<CustomerEntityRequest> customerResponse = restTemplate
				.getForEntity(StringConstants.GET_CUSTOMER_URL.concat(custId.toString()), CustomerEntityRequest.class);
		log.info("Response from get " + customerResponse.getBody() + " \n " + customerResponse.getStatusCode().value());
		if (customerResponse.getStatusCode().value() == 200 && Objects.nonNull(customerResponse.getBody())) {
			return customerResponse.getBody();
		}
		return null;
	}

	public ResponseEntity<?> deleteCustomerFromAccount(Integer custId) {
		ResponseEntity<String> customerResponse = restTemplate
				.getForEntity(StringConstants.GET_CUSTOMER_URL.concat(custId.toString()), String.class);
		log.info("Response from get " + customerResponse.getBody() + " \n status code"
				+ customerResponse.getStatusCode().value());
		return customerResponse;
	}

	@Override
	public ResponseEntity<String> createAccount(CustomerEntityRequest customerRequest) {
		log.info(StringConstants.CREATE_ACCOUNT_STARTED);
		AccountEntity entity = new AccountEntity();
		entity.setCustomerModel(modelMapper.map(customerRequest, CustomerModel.class));
		entity.setAccountNumber(customerRequest.getCustAccountNumber());
		repository.save(entity);
		log.info(StringConstants.CREATE_ACCOUNT_STARTED);
		return new ResponseEntity<>("Customer Added", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> transaction(TransactionRequest request) {
		log.info(StringConstants.TRANSACTION_STARTED);
		if (request.getAmount() < 0) {
			return new ResponseEntity<>("Amount should be greater than or equal to 0", HttpStatus.BAD_REQUEST);
		}
		log.info("Request " + request);
		CustomerEntityRequest customerResponse = checkIfCustomerExists(request.getCustId());
		if (Objects.nonNull(customerResponse)) {
			AccountEntity entity = repository.findById(request.getAccountNumber()).get();
			if (Objects.nonNull(entity)) {
				switch (request.getTransactionType()) {
				case 'D': {
					entity.setAccountBalance(entity.getAccountBalance() + request.getAmount());
					entity.setLastModifiedDate(new Date(System.currentTimeMillis()));
					repository.save(entity);
					log.info(StringConstants.TRANSACTION_END);
					return ResponseEntity.ok("Amount successfully deposited");
				}
				case 'W': {
					if (entity.getAccountBalance() >= request.getAmount()) {
						entity.setAccountBalance(entity.getAccountBalance() - request.getAmount());
						entity.setLastModifiedDate(new Date(System.currentTimeMillis()));
						repository.save(entity);
						log.info(StringConstants.TRANSACTION_END);
						return ResponseEntity.ok("Amount successfully deducted");
					} else
						return new ResponseEntity<>("Insufficient balance", HttpStatus.BAD_REQUEST);
				}
				default: {
					return new ResponseEntity<>("Transaction type must be W for withdrawl or D for deposit",
							HttpStatus.BAD_REQUEST);
				}
				}
			} else {
				return new ResponseEntity<>("No such account exists", HttpStatus.BAD_REQUEST);
			}

		} else {
			return new ResponseEntity<>("No customer is associated with the given ID", HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<?> getAccountDetails(Integer custId, String accountNumber) {
		log.info(StringConstants.GET_ACCOUNT_STARTED);
		AccountEntity entity;
		try {
			CustomerEntityRequest customerResponse = checkIfCustomerExists(custId);
			if (Objects.nonNull(customerResponse)) {
				entity = repository.findById(accountNumber).get();
				if (Objects.nonNull(entity)) {
					entity.setCustomerModel(modelMapper.map(customerResponse, CustomerModel.class));
				}
			} else
				return new ResponseEntity<>("No customer is associated with the requested id", HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		log.info(StringConstants.GET_ACCOUNT_END);
		return ResponseEntity.ok(entity);
	}

	@Override
	public ResponseEntity<?> deleteAccount(Integer custId, String accountNumber) {
		log.info(StringConstants.DELETE_ACCOUNT_STARTED);
		try {
			if (custId == null || StringUtils.isBlank(accountNumber)) {
				return new ResponseEntity<>("Both parameters are required", HttpStatus.BAD_REQUEST);
			}
			CustomerEntityRequest customerResponse = checkIfCustomerExists(custId);
			AccountEntity entity = repository.findById(accountNumber).get();

			if (Objects.equals(customerResponse.getCustAccountNumber(), entity.getAccountNumber())) {
				deleteCustomerFromAccount(custId);
				repository.deleteById(accountNumber);
			} else {
				return new ResponseEntity<>("Please check your parameters", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		log.info(StringConstants.DELETE_ACCOUNT_END);
		return ResponseEntity.ok("Account Deleted!");
	}

	@Override
	public ResponseEntity<?> getAccountDetailsForDelete(String accountNumber) {
		return ResponseEntity.ok(repository.findById(accountNumber).get());
	}

	@Override
	public ResponseEntity<?> deleteAccountFromCustomer(String accountNumber) {
		repository.deleteById(accountNumber);
		return ResponseEntity.ok("Account deleted!");
	}

}
