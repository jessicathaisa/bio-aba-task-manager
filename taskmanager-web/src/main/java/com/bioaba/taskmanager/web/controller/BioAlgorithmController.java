package com.bioaba.taskmanager.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bioaba.taskmanager.core.facade.BioAlgorithmFacade;
import com.bioaba.taskmanager.persistence.entity.BioAlgorithm;
import com.bioaba.taskmanager.web.controller.base.AbstractCrudController;
import com.bioaba.taskmanager.web.validation.BioAlgorithmValidator;

@ControllerAdvice
@RequestMapping("/bioalgorithm")
public class BioAlgorithmController extends AbstractCrudController<BioAlgorithm> {

	@Inject
	public BioAlgorithmController(BioAlgorithmFacade facade, BioAlgorithmValidator validator){
		super(facade, validator);
	}
	
}
