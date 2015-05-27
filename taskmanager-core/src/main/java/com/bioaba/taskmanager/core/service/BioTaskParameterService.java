package com.bioaba.taskmanager.core.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bioaba.taskmanager.core.service.base.AbstractCrudService;
import com.bioaba.taskmanager.persistence.entity.BioTaskParameter;
import com.bioaba.taskmanager.persistence.repository.IBioTaskParameterRepository;

@Service
public class BioTaskParameterService extends AbstractCrudService<BioTaskParameter> {
	protected IBioTaskParameterRepository repository;

	@Inject
	public BioTaskParameterService(IBioTaskParameterRepository repository) {
		super(repository);
		this.repository = repository;	
	}
}
