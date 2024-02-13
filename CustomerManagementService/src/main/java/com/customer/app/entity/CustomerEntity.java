package com.customer.app.entity;

import java.sql.Date;

import javax.validation.constraints.Size;

import com.customer.app.configuration.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerEntity extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int custId;
	
	@Column(length = 14, unique = true, updatable = false)
	private String custAccountNumber = "14000000";

	@Column(nullable = false)
	private String custName;

	@Column(nullable = false)
	private String custAddress;

	@Column(nullable = false)
	@Size(min = 10, max = 10)
	private Long custPhone;

	@Column(nullable = false)
	private Date custDob;

}
