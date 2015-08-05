package com.bioaba.taskmanager.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bioaba.taskmanager.core.facade.UserFacade;
import com.bioaba.taskmanager.persistence.entity.User;
import com.bioaba.taskmanager.web.validation.UserValidator;

@ControllerAdvice
@RequestMapping("/user")
public class UserController {
	UserFacade facade;

	@Inject
	public UserController(UserFacade facade, UserValidator validator) {
		// super(facade, validator);
		this.facade = facade;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public void test(Exception ex) {
		System.out.println(ex.toString());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> loginRequest(@RequestParam("login") String login,
			@RequestParam("password") String password, HttpServletRequest request) {
		User user = this.facade.login(login, password);
		if(user == null) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		} else {
			request.getSession().setAttribute("usuarioLogado", true);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void logoutRequest(HttpServletRequest request) {
		request.getSession().setAttribute("usuarioLogado", false);
	}

}
