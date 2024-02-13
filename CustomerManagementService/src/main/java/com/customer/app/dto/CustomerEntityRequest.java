package com.customer.app.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomerEntityRequest {

	private Integer custId;
	private String custAccountNumber;
	private String custName;
	private String custAddress;
	private Long custPhone;
	private Date custDob;

}
