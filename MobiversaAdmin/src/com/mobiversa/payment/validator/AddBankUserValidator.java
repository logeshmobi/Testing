package com.mobiversa.payment.validator;

import com.mobiversa.common.bo.BankUser;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddBankUserValidator implements Validator {
	@Override
	public boolean supports(Class<?> classType) {
		return classType.isAssignableFrom(BankUser.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		BankUser command = null;

		command = (BankUser) obj;

		/*
		 * if (command.getEmail() < = 0) { errors.rejectValue("email",
		 * "email.invalid"); }
		 */

		if (command.getUsername() == null || command.getUsername().equals("")) {
			errors.rejectValue("username", "username.blank");
		}
	}
}
