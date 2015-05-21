package com.bioaba.taskmanager.core.facade;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.core.facade.base.AbstractCrudFacade;
import com.bioaba.taskmanager.core.service.BioDatabaseService;
import com.bioaba.taskmanager.persistence.entity.BioDatabase;

@Component
@Transactional
public class BioDatabaseFacade extends AbstractCrudFacade<BioDatabase> {

	@Autowired
	public BioDatabaseFacade(BioDatabaseService studentService) {
		super(studentService);
	}
	
}
