package com.customer.app.entity.id;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CustomerEntityId {

	private int id; // auto
	private String custAccountNumber = "14000000"; // fix first 10 digits

}
