package com.bioaba.taskmanager.core.facade.base;

import java.util.List;

import com.bioaba.taskmanager.core.service.base.AbstractCrudService;

public class AbstractCrudFacade<T> {
	private AbstractCrudService<T> abstractCrud;
	
	public AbstractCrudFacade(AbstractCrudService<T> abstractCrud){
		this.abstractCrud = abstractCrud;
	}

	public List<T> list() {
		return this.abstractCrud.list();
	}
	
	public T save (T entity){
		abstractCrud.save(entity);
		return entity;
	}
}
