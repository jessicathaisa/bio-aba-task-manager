package com.bioaba.taskmanager.web.controller;

import java.util.Base64;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.context.ApplicationEventPublisher;
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

import com.bioaba.taskmanager.core.events.BioTaskSavedEvent;
import com.bioaba.taskmanager.core.facade.BioTaskFacade;
import com.bioaba.taskmanager.persistence.entity.BioTask;
import com.bioaba.taskmanager.web.controller.base.AbstractCrudController;
import com.bioaba.taskmanager.web.validation.BioTaskValidator;

@ControllerAdvice
@RequestMapping("/biotask")
public class BioTaskController {

	private BioTaskFacade facade;

	@Inject
	private ApplicationEventPublisher eventPublisher;
	
	@Inject
	public BioTaskController(BioTaskFacade facade, BioTaskValidator validator) {
		this.facade = facade;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public void test(Exception ex) {
		System.out.println(ex.toString());
	}

	@RequestMapping(value="/{taskKey}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody BioTask getOne(@PathVariable("taskKey") String taskKey) {
		return facade.findByTaskKey(taskKey);
	}

	@RequestMapping(value="/{taskKey}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("taskKey") String taskKey, @RequestBody Map<String, String> map) {
		String result = (String) map.get("result");
		String taskStatus = (String) map.get("status");
		facade.updateTaskStatusByTaskKey(taskKey, taskStatus, Base64.getDecoder().decode(result));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody BioTask save(
			@RequestPart(value = "file", required = false) MultipartFile queryFile,
			@RequestPart(value = "query", required = false) String queryText,
			@RequestPart(value = "task", required = true)  BioTask task) {
		
		try{
			byte[] bytearray;
			
			if(queryFile != null){
				bytearray = queryFile.getBytes();
			}
			else{
				bytearray = queryText.getBytes();
			}

			facade.saveTask(task, bytearray);
			

			eventPublisher.publishEvent(new BioTaskSavedEvent(this, task
					.getTaskKey()));
		}
		catch(Exception ex){
			
		}
		
		return task;
	}
}
