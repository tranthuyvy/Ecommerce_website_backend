package com.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

}
