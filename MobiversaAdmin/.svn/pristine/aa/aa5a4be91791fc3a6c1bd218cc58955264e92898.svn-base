package com.mobiversa.payment.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mobiversa.common.bo.Agent;

@Component
public class AddAgentUserValidator implements Validator {
	@Override
	public boolean supports(Class<?> classType) {
		return classType.isAssignableFrom(Agent.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Agent command = null;

		command = (Agent) obj;
		/*
		if (!command.matches()) {
			errors.rejectValue("email", command.getMailId(), "email address is invalid");
	        throw new BadRequestException("email address is invalid");
	    }

	    Agent agent = AgentService.getUserByEmail(command.getEmail());
	    if (agent != null) {
	    	errors.rejectValue("email", command.getMailId(), "user already exist");
	        throw new ValidationException("User Exist");
	    }
*/
		
		if (command.getMailId().equals("")) {
			errors.rejectValue("mailId", "mailId.invalid");
		}
		if (command.getFirstName() == null || command.getFirstName().equals("")) {
			errors.rejectValue("firstName", "firstName.blank");
		}
	}
	
	/*	Merchant command1 = null;

		command1= (Merchant) obj;
		
		
		if (command.getEmail().equals("")) {
			errors.rejectValue("email", "email.invalid");
	}
	
	}*/
}

	
	


