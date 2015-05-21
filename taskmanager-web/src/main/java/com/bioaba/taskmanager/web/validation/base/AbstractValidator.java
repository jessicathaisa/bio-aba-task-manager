package com.bioaba.taskmanager.web.validation.base;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractValidator<T> implements Validator {

	private Class<T> targetClass;
	private Errors errors;

	public AbstractValidator(Class<T> targetClass) {
		this.targetClass = targetClass;
	}

	protected Errors getErrors() {
		return errors;
	}


	public boolean supports(Class<?> arg0) {
		return targetClass.isAssignableFrom(arg0);
	}

	@SuppressWarnings("unchecked")
	public void validate(Object arg0, Errors errors) {
		this.errors = errors;

		validate((T) arg0);
	}

	protected void Required(String field) {
		Object fieldValue = errors.getFieldValue(field);
		if (fieldValue == null || !StringUtils.hasText(fieldValue.toString())) {

		}
	}

	protected abstract void validate(T entity);
}
