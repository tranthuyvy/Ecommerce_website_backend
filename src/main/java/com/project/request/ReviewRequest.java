package com.project.request;

public class ReviewRequest {
	
	private Long productId;

	private String review;

	//getter and setter

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
}
