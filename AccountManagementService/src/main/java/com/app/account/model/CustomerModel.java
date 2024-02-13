package com.app.account.model;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomerModel {

	private Integer custId;
	private String custName;
	private String custAddress;
	private Long custPhone;
	private Date custDob;
}
