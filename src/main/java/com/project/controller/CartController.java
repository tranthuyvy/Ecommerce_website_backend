package com.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.exception.ProductException;
import com.project.exception.UserException;
import com.project.modal.Cart;
import com.project.modal.User;
import com.project.request.AddItemRequest;
import com.project.response.ApiResponse;
import com.project.service.CartService;
import com.project.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	private CartService cartService;
	private UserService userService;
	
	public CartController(CartService cartService,UserService userService) {
		this.cartService = cartService;
		this.userService = userService;
	}
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Cart cart = cartService.findUserCart(user.getId());
		
		System.out.println("cart - " + cart.getUser().getEmail());
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		cartService.addCartItem(user.getId(), req);
		
		ApiResponse res = new ApiResponse("Item Add To Cart",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
		
	}

	@PostMapping("/clear")
	public ResponseEntity<ApiResponse> clearCart(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		cartService.clearCart(user.getId());
		return new ResponseEntity<>(new ApiResponse("Cart cleared successfully", true), HttpStatus.OK);
	}
}
