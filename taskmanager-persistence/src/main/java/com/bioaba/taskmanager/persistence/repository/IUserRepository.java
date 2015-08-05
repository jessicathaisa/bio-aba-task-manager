package com.bioaba.taskmanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bioaba.taskmanager.persistence.entity.User;

public interface IUserRepository extends JpaRepository<User, Long>{

	User findByLoginAndPassword(String login, String password);
	
}
