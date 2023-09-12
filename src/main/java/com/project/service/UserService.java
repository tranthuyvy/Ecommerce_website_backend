package com.project.service;

import com.project.exception.UserException;
import com.project.modal.User;

import java.util.List;

public interface UserService {

	public List<User> getAllUsers() throws UserException;
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;

	public User updateUserProfileByJwt (String jwt, User updateUser) throws UserException;

}
