package com.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.exception.OrderException;
import com.project.modal.Order;
import com.project.response.ApiResponse;
import com.project.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
	
	private OrderService orderService;
	
	public AdminOrderController(OrderService orderService) {
		this.orderService=orderService;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler(){
		List<Order> orders = orderService.getAllOrders();
		
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		Order order = orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		Order order = orderService.shippedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		Order order = orderService.deliveredOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> canceledOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		Order order = orderService.cancledOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	@PutMapping("/{orderId}/success")
	public ResponseEntity<Order> successOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		Order order = orderService.successOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		orderService.deleteOrder(orderId);
		ApiResponse res = new ApiResponse("Order Deleted Success",true);
		System.out.println("Delete method working....");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/update-payment-status")
	public ResponseEntity<ApiResponse> updateOrderPaymentStatus(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
		orderService.updateOrder(orderId);
		ApiResponse res = new ApiResponse("Update Payment Status Success",true);
		System.out.println("Update payment status working....");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
}
