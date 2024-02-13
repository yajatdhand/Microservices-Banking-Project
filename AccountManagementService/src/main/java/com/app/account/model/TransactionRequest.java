package com.app.account.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TransactionRequest {

	@NotNull
	private Integer custId;
	@NotNull
	private String accountNumber;
	@NotNull
	private char transactionType;
	@NotNull
	private Double amount;

}
