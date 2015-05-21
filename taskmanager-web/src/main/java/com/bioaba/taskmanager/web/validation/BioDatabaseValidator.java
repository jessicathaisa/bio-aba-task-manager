package com.bioaba.taskmanager.web.validation;

import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.persistence.entity.BioDatabase;
import com.bioaba.taskmanager.web.validation.base.AbstractValidator;

@Component
public class BioDatabaseValidator extends AbstractValidator<BioDatabase>{

	public BioDatabaseValidator() {
		super(BioDatabase.class);
	}

	@Override
	protected void validate(BioDatabase entity) {

		super.Required("name");
		
	}

}
