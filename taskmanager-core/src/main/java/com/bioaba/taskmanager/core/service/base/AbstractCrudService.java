package com.bioaba.taskmanager.core.service.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractCrudService<T> {

	private JpaRepository<T, Long> repository;

	protected JpaRepository<T, Long> getRepository() {
		return repository;
	}

	public AbstractCrudService(JpaRepository<T, Long> repository) {
		this.repository = repository;
	}

	public List<T> list() {
		return repository.findAll();
	}

	
}