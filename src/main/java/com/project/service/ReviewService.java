package com.project.service;

import java.util.List;

import com.project.exception.ProductException;
import com.project.modal.Review;
import com.project.modal.User;
import com.project.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req, User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);
	
	
}
