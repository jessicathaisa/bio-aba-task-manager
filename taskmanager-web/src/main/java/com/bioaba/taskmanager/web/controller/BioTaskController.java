package com.bioaba.taskmanager.web.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

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

	@RequestMapping(value="/{taskKey}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody BioTask getOne(@PathVariable("taskKey") String taskKey) {
		return facade.findByTaskKey(taskKey);
	}

	@RequestMapping(value="/{taskKey}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("taskKey") String taskKey, @RequestBody String taskStatus) {
		facade.updateTaskStatusByTaskKey(taskKey, taskStatus);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody BioTask save(
			@RequestPart(value = "file", required = false) MultipartFile queryFile,
			@RequestPart(value = "query", required = false) String queryText,
			@RequestPart(value = "task", required = true)  BioTask task) {
		
		if(queryFile != null)
			facade.saveTask(task, queryFile);
		else
			facade.saveTask(task, queryText);
		return task;
	}
}
