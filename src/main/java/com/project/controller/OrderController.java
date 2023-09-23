package com.project.controller;

import java.util.List;

import com.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.exception.OrderException;
import com.project.exception.UserException;
import com.project.modal.Address;
import com.project.modal.Order;
import com.project.modal.User;
import com.project.service.OrderService;
import com.project.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private OrderService orderService;
	private UserService userService;

	@Autowired
	private OrderRepository orderRepository;
	
	public OrderController(OrderService orderService,UserService userService) {
		this.orderService=orderService;
		this.userService=userService;
	}
	
	@PostMapping("/")
	public ResponseEntity<Order> createOrderHandler(@RequestBody Address spippingAddress,
			@RequestHeader("Authorization")String jwt) throws UserException{
		
		User user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.createOrder(user, spippingAddress);
		
		return new ResponseEntity<Order>(order,HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity< List<Order> > usersOrderHistoryHandler(@RequestHeader("Authorization")
	String jwt) throws OrderException, UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		List<Order> orders=orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity< Order> findOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") 
	String jwt) throws OrderException, UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		Order orders=orderService.findOrderById(orderId);
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}

	@GetMapping("/placed")
	public ResponseEntity<List<Order>> getPlacedOrdersForUser(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Order> placedOrders = orderRepository.getPlacedOrders(user.getId());
		return new ResponseEntity<>(placedOrders, HttpStatus.OK);
	}

	@GetMapping("/confirmed")
	public ResponseEntity<List<Order>> getConfirmedOrdersForUser(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Order> confirmedOrders = orderRepository.getConfirmedOrders(user.getId());
		return new ResponseEntity<>(confirmedOrders, HttpStatus.OK);
	}

	@GetMapping("/shipped")
	public ResponseEntity<List<Order>> getShippedOrdersForUser(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Order> shippedOrders = orderRepository.getShippedOrders(user.getId());
		return new ResponseEntity<>(shippedOrders, HttpStatus.OK);
	}

	@GetMapping("/delivered")
	public ResponseEntity<List<Order>> getDeliveredOrdersForUser(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Order> deliveredOrders = orderRepository.getDeliveredOrders(user.getId());
		return new ResponseEntity<>(deliveredOrders, HttpStatus.OK);
	}

	@GetMapping("/cancelled")
	public ResponseEntity<List<Order>> getCancelledOrdersForUser(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Order> cancelledOrders = orderRepository.getCancelledOrders(user.getId());
		return new ResponseEntity<>(cancelledOrders, HttpStatus.OK);
	}

	@GetMapping("/success")
	public ResponseEntity<List<Order>> getSuccessOrdersForUser(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Order> successOrders = orderRepository.getSuccessOrders(user.getId());
		return new ResponseEntity<>(successOrders, HttpStatus.OK);
	}
}
