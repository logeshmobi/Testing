package com.mobiversa.payment.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mobiversa.common.bo.Merchant;

public class AddMerchantUserValidator implements Validator {
	@Override
	public boolean supports(final Class<?> classType) {
		return classType.isAssignableFrom(Merchant.class);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		Merchant command = null;

		command = (Merchant) obj;

		if (command.getEmail().equals("")) {
			errors.rejectValue("email", "email.invalid");
		}

		/*
		 * if ((command.getPostcode == null) || command.getPostcode.equals(""))
		 * { errors.rejectValue("username", "username.blank"); }
		 */
	}
}