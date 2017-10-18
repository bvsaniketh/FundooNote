package com.bridgeit.Validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bridgeit.Model.Register;

@Component
public class FormValidator implements Validator {

	public boolean supports(Class clazz) {
		return Register.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		Register reg=(Register) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","error.name", "name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile","error.mobile", "mobile is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email","error.email", "email is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","error.password", "password is required");
	
	}

}
