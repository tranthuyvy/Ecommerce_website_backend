package com.project.service;

import com.project.exception.ProductException;
import com.project.modal.Cart;
import com.project.modal.User;
import com.project.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);

	public void clearCart(Long userId);
}
