package com.bioaba.taskmanager.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bioaba.taskmanager.core.facade.BioDatabaseFacade;
import com.bioaba.taskmanager.persistence.entity.BioDatabase;
import com.bioaba.taskmanager.web.controller.base.AbstractCrudController;
import com.bioaba.taskmanager.web.validation.BioDatabaseValidator;

@ControllerAdvice
@RequestMapping("/biodatabase")
public class BioDatabaseController extends AbstractCrudController<BioDatabase> {

	@Inject
	public BioDatabaseController(BioDatabaseFacade facade, BioDatabaseValidator validator){
		super(facade, validator);
	}
	
}
