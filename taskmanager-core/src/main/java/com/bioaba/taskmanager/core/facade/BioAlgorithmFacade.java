package com.bioaba.taskmanager.core.facade;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.core.facade.base.AbstractCrudFacade;
import com.bioaba.taskmanager.core.service.BioAlgorithmService;
import com.bioaba.taskmanager.persistence.entity.BioAlgorithm;

@Component
@Transactional
public class BioAlgorithmFacade extends AbstractCrudFacade<BioAlgorithm>{

	@Autowired
	public BioAlgorithmFacade(BioAlgorithmService abstractCrud) {
		super(abstractCrud);
		// TODO Auto-generated constructor stub
	}

}
