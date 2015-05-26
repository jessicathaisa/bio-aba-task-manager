package com.bioaba.taskmanager.web.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bioaba.taskmanager.core.facade.BioTaskFacade;
import com.bioaba.taskmanager.persistence.entity.BioTask;
import com.bioaba.taskmanager.web.controller.base.AbstractCrudController;
import com.bioaba.taskmanager.web.validation.BioTaskValidator;

@ControllerAdvice
@RequestMapping("/biotask")
public class BioTaskController extends AbstractCrudController<BioTask> {

	private BioTaskFacade facade;	
	
	@Inject
	public BioTaskController(BioTaskFacade facade, BioTaskValidator validator) {
		super(facade, validator);
		this.facade = facade;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public void test(Exception ex) {
		ex.toString();
	}

	@RequestMapping(value="/{taskKey}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("taskKey") String taskKey, @RequestBody String taskStatus) {
		facade.updateTaskStatusByTaskKey(taskKey, taskStatus);
	}
}
