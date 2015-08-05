package com.bioaba.taskmanager.core.facade;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.core.facade.base.AbstractCrudFacade;
import com.bioaba.taskmanager.core.service.UserService;
import com.bioaba.taskmanager.persistence.entity.User;

@Component
@Transactional
public class UserFacade extends AbstractCrudFacade<User> {
	private UserService service;
	
	@Autowired
	public UserFacade(UserService service) {
		super(service);
		this.service = service;
	}

	public User login(String userName, String password){
		return service.login(userName, password);
	}
	
}
