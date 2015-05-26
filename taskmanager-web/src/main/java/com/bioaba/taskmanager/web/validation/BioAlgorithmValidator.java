package com.bioaba.taskmanager.web.validation;

import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.persistence.entity.BioAlgorithm;
import com.bioaba.taskmanager.web.validation.base.AbstractValidator;

@Component
public class BioAlgorithmValidator extends AbstractValidator<BioAlgorithm>{

	public BioAlgorithmValidator() {
		super(BioAlgorithm.class);
	}

	@Override
	protected void validate(BioAlgorithm entity) {

		super.Required("name");
		
	}

}
