package com.bioaba.taskmanager.core.service;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.bioaba.taskmanager.core.service.base.AbstractCrudService;
import com.bioaba.taskmanager.persistence.entity.User;
import com.bioaba.taskmanager.persistence.repository.IUserRepository;

@Service
public class UserService extends AbstractCrudService<User>{

	private IUserRepository repository;
	
	@Inject
	public UserService(IUserRepository repository) {
		super(repository);
		this.repository = repository;
	}
	
	public User login(String userName, String password){
		String securedPassword = DigestUtils.md5Hex(password);
		return this.repository.findByLoginAndPassword(userName, securedPassword);
	}

}
