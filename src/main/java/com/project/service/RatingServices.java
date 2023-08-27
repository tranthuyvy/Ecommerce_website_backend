package com.project.service;

import java.util.List;

import com.project.exception.ProductException;
import com.project.modal.Rating;
import com.project.modal.User;
import com.project.request.RatingRequest;

public interface RatingServices {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);

}
