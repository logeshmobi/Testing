package com.mobiversa.payment.validate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mobiversa.common.bo.Merchant;

public class MerchantValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Merchant.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
		 Merchant emp = (Merchant) target;
	        if(emp.getId() <=0){
	            errors.rejectValue("id", "negativeValue", new Object[]{"'id'"}, "id can't be negative");
	        }
	         
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "businessRegistrationNumber", "businessRegistrationNumber.required");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "businessShortName", "businessShortName.required");
	    
	}
	 
	       
	       
}
