package com.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.exception.ProductException;
import com.project.exception.UserException;
import com.project.modal.Review;
import com.project.modal.User;
import com.project.request.ReviewRequest;
import com.project.service.ReviewService;
import com.project.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	private ReviewService reviewService;
	private UserService userService;
	
	public ReviewController(ReviewService reviewService,UserService userService) {
		this.reviewService = reviewService;
		this.userService = userService;
	}
	@PostMapping("/create")
	public ResponseEntity<Review> createReviewHandler(@RequestBody ReviewRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		System.out.println("product id " + req.getProductId() + " - "+ req.getReview());
		Review review = reviewService.createReview(req, user);
		System.out.println("product review " + req.getReview());
		return new ResponseEntity<Review>(review,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductsReviewHandler(@PathVariable Long productId){
		List<Review>reviews = reviewService.getAllReview(productId);
		return new ResponseEntity<List<Review>>(reviews,HttpStatus.OK);
	}

}
