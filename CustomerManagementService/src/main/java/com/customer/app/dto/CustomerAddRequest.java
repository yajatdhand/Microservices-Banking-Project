package com.customer.app.dto;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CustomerAddRequest {

	private Integer custId;
	@NotNull
	private String custAccountNumber;
	@NotNull
	private String custName;
	@NotNull
	private String custAddress;
	@NotNull
	@Column(length = 10)
	private Long custPhone;
	@NotNull
	private Date custDob;
}
