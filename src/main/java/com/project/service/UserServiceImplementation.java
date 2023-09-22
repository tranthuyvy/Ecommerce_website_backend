package com.project.service;

import java.util.List;
import java.util.Optional;

import com.project.user.domain.Rank;
import org.springframework.stereotype.Service;

import com.project.config.JwtTokenProvider;
import com.project.exception.UserException;
import com.project.modal.User;
import com.project.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
	
	private UserRepository userRepository;
	private JwtTokenProvider jwtTokenProvider;
	
	public UserServiceImplementation(UserRepository userRepository,JwtTokenProvider jwtTokenProvider) {
		
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		
	}

	@Override
	public User findUserById(Long userId) throws UserException {

		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()){
			return user.get();
		}

		throw new UserException("user not found with id "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		System.out.println("user service");
		String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
		
		System.out.println("email" + email);
		
		User user = userRepository.findByEmail(email);

		if(user == null) {
			throw new UserException("user not exist with email " + email);
		}
		System.out.println("email user" + user.getEmail());
		return user;
	}

	@Override
	public User updateUserProfileByJwt(String jwt, User updatedUser) throws UserException {

		String email = jwtTokenProvider.getEmailFromJwtToken(jwt);

		User currentUser = userRepository.findByEmail(email);

		if (currentUser == null) {
			throw new UserException("Người dùng không tồn tại với email " + email);
		}

		if (updatedUser.getFirstName() != null) {
			currentUser.setFirstName(updatedUser.getFirstName());
		}

		if (updatedUser.getLastName() != null) {
			currentUser.setLastName(updatedUser.getLastName());
		}

		if (updatedUser.getMobile() != null) {
			currentUser.setMobile(updatedUser.getMobile());
		}


		if (!currentUser.getEmail().equals(updatedUser.getEmail())) {

			User existingUserWithEmail = userRepository.findByEmail(updatedUser.getEmail());
			if (existingUserWithEmail != null) {
				throw new UserException("Email đã tồn tại");
			}
			if (updatedUser.getEmail() != null) {
				currentUser.setEmail(updatedUser.getEmail());
			}
		}

		currentUser.setPoints(updatedUser.getPoints());
		if (updatedUser.getPoints() >= 0 && updatedUser.getPoints() < 200) {
			currentUser.setRank(Rank.BRONZE);
		} else if (updatedUser.getPoints() >= 200 && updatedUser.getPoints() < 400) {
			currentUser.setRank(Rank.SILVER);
		} else if (updatedUser.getPoints() >= 400 && updatedUser.getPoints() < 600) {
			currentUser.setRank(Rank.GOLD);
		} else {
			currentUser.setRank(Rank.DIAMOND);
		}

		return userRepository.save(currentUser);
	}

	@Override
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

}
