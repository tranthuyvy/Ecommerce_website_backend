package com.project.request;

public class ReviewRequest {
	
	private Long productId;

	private String review;

	private float star;

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

	public float getStar() {
		return star;
	}

	public void setStar(float star) {
		this.star = star;
	}
}
