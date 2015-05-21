package com.bioaba.taskmanager.core.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bioaba.taskmanager.core.service.base.AbstractCrudService;
import com.bioaba.taskmanager.persistence.entity.BioDatabase;
import com.bioaba.taskmanager.persistence.repository.IBioDatabaseRepository;

@Service
public class BioDatabaseService extends AbstractCrudService<BioDatabase> {
	protected IBioDatabaseRepository repository;

	@Inject
	public BioDatabaseService(IBioDatabaseRepository repository) {
		super(repository);
		this.repository = repository;	
	}
}
