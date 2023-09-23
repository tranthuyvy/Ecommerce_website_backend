package com.project.service;

import java.util.List;

import com.project.exception.OrderException;
import com.project.modal.Address;
import com.project.modal.Order;
import com.project.modal.User;

public interface OrderService {
	
	public Order createOrder(User user, Address shippingAdress);
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory(Long userId);
	
	public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId)throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	
	public Order deliveredOrder(Long orderId) throws OrderException;
	
	public Order cancledOrder(Long orderId) throws OrderException;
	
	public List<Order>getAllOrders();
	
	public Order deleteOrder(Long orderId) throws OrderException;

	public Order updateOrder (Long orderId) throws OrderException;
	public Order successOrder (Long orderId) throws OrderException;
	
}
