package com.app.account.entity;

import javax.validation.constraints.Min;

import com.app.account.configuration.Auditable;
import com.app.account.model.CustomerModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountEntity extends Auditable {

	@Id
	@Column(length = 14, unique = true, updatable = false)
	private String accountNumber = "14000000";

	@Column(nullable = false)
	private String accountType = "Savings";

	@Min(value = 0)
	@Column(nullable = false)
	private Double accountBalance = 0.0;

	@Transient
	private CustomerModel customerModel;

}
