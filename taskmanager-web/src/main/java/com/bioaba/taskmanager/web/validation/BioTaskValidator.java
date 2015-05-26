package com.bioaba.taskmanager.web.validation;

import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.persistence.entity.BioTask;
import com.bioaba.taskmanager.web.validation.base.AbstractValidator;

@Component
public class BioTaskValidator extends AbstractValidator<BioTask> {

	public BioTaskValidator() {
		super(BioTask.class);
	}

	@Override
	protected void validate(BioTask entity) {
	}
}
