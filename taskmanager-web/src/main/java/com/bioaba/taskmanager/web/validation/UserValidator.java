package com.bioaba.taskmanager.web.validation;

import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.web.validation.base.AbstractValidator;

@Component
public class UserValidator extends AbstractValidator<UserValidator>{

	public UserValidator() {
		super(UserValidator.class);
	}

	@Override
	protected void validate(UserValidator entity) {

		super.Required("login");
		
	}

}
