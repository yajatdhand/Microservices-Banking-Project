package com.app.account.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class AccountUtils {

	Random rnd = new Random();

	public String generateAccountNumber(String accountNumber) {
		int number = rnd.nextInt(999999);
		return accountNumber + String.format("%06d", number);
	}

}
