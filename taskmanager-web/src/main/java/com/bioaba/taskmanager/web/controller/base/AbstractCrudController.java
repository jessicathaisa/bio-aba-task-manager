package com.bioaba.taskmanager.web.controller.base;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bioaba.taskmanager.core.facade.base.AbstractCrudFacade;

public abstract class AbstractCrudController<T> {

	protected AbstractCrudFacade<T> facade;
	private Validator validator;
	

	public AbstractCrudController(AbstractCrudFacade<T> facade,
			Validator validator) {
		this.facade = facade;
		this.validator = validator;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// Set @Valid to use
		//binder.setValidator(validator);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<T> list() {
		return facade.list();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody T save(@RequestBody T entity) {
		facade.save(entity);
		return entity;
	}

}
