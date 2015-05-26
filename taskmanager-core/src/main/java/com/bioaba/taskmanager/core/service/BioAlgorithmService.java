package com.bioaba.taskmanager.core.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bioaba.taskmanager.core.service.base.AbstractCrudService;
import com.bioaba.taskmanager.persistence.entity.BioAlgorithm;
import com.bioaba.taskmanager.persistence.repository.IBioAlgorithmRepository;

@Service
public class BioAlgorithmService extends AbstractCrudService<BioAlgorithm>{

	@Inject
	public BioAlgorithmService(IBioAlgorithmRepository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
	}

}
