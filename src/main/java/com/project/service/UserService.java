package com.project.service;

import com.project.exception.UserException;
import com.project.modal.User;

public interface UserService {
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;

	public User updateUserProfileByJwt (String jwt, User updateUser) throws UserException;

}
