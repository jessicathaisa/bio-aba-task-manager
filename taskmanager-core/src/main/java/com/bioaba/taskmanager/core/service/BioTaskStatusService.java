package com.bioaba.taskmanager.core.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bioaba.taskmanager.core.service.base.AbstractCrudService;
import com.bioaba.taskmanager.persistence.entity.BioTaskStatus;
import com.bioaba.taskmanager.persistence.repository.IBioTaskStatusRepository;

@Service
public class BioTaskStatusService extends AbstractCrudService<BioTaskStatus> {

	protected IBioTaskStatusRepository repository;

	@Inject
	public BioTaskStatusService(IBioTaskStatusRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public BioTaskStatus findByName(String name) {
		return repository.findByName(name);
	}

}
