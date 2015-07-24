package com.bioaba.taskmanager.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bioaba.taskmanager.core.facade.BioTaskFacade;
import com.bioaba.taskmanager.persistence.entity.BioTask;
import com.bioaba.taskmanager.web.controller.base.AbstractCrudController;
import com.bioaba.taskmanager.web.validation.BioTaskValidator;

@ControllerAdvice
@RequestMapping("/biotaskresult")
public class BioTaskResultController extends AbstractCrudController<BioTask> {

	private BioTaskFacade facade;
	
	@Inject
	public BioTaskResultController(BioTaskFacade facade, BioTaskValidator validator){
		super(facade, validator);
		this.facade = facade;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public void test(Exception ex) {
		ex.toString();
	}

	@RequestMapping(value="/{taskKey}", method = RequestMethod.GET, produces= "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, String> getOne(@PathVariable("taskKey") String taskKey) {
		Map<String, String> result = new HashMap<String, String>();
		String resultasd = facade.findResultByTaskKey(taskKey);

		result.put("status", facade.findByTaskKey(taskKey).getStatus().getName());
		result.put("result", resultasd);
		return result;
	}
	
}
