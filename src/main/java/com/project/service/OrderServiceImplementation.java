package com.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.exception.ProductException;
import com.project.modal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.exception.OrderException;
import com.project.repository.AddressRepository;
import com.project.repository.OrderItemRepository;
import com.project.repository.OrderRepository;
import com.project.repository.UserRepository;
import com.project.user.domain.OrderStatus;
import com.project.user.domain.PaymentStatus;

@Service
public class OrderServiceImplementation implements OrderService {
	
	private OrderRepository orderRepository;
	private CartService cartService;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;
	private ProductService productService;

	@Autowired
	public OrderServiceImplementation(OrderRepository orderRepository,CartService cartService,
			AddressRepository addressRepository,UserRepository userRepository,
			OrderItemService orderItemService,OrderItemRepository orderItemRepository, ProductService productService) {
		this.orderRepository=orderRepository;
		this.cartService=cartService;
		this.addressRepository=addressRepository;
		this.userRepository=userRepository;
		this.orderItemService=orderItemService;
		this.orderItemRepository=orderItemRepository;
		this.productService = productService;
	}

	@Override
	public Order createOrder(User user, Address shippAddress) {
		
		shippAddress.setUser(user);
		Address address = addressRepository.save(shippAddress);
		user.getAddresses().add(address);
		userRepository.save(user);
		
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem item: cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());

			OrderItem createdOrderItem = orderItemRepository.save(orderItem);

			Product product = item.getProduct();
			int remainingQuantity = product.getQuantity() - item.getQuantity();

			if (remainingQuantity >= 0) {
				product.setQuantity(remainingQuantity);
				try {
					productService.updateProduct(product.getId(), product);
				} catch (ProductException e) {
					System.out.println("Error when update product");
				}
			} else {
				System.out.println("Error quantity");
			}

			orderItems.add(createdOrderItem);
		}

		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscounte(cart.getDiscounte());
		createdOrder.setTotalItem(cart.getTotalItem());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus(OrderStatus.PLACED);
		createdOrder.setPaymentStatus(0);
//		createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder = orderRepository.save(createdOrder);
		
		for (OrderItem item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
		
	}

	@Override
	public Order updateOrder(Long orderId) throws OrderException {
		System.out.println("update payment status");
		Order order = findOrderById(orderId);
		order.setPaymentStatus(1);
		return orderRepository.save(order);
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.PLACED);
		order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CONFIRMED);

		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.SHIPPED);
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.DELIVERED);
		order.setDeliveryDate(LocalDateTime.now());
		return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}
	@Override
	public Order successOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.SUCCESS);
		return orderRepository.save(order);
	}

	@Override
	public Order deleteOrder(Long orderId) throws OrderException {
//		Order order = findOrderById(orderId);

//		orderRepository.deleteById(orderId);
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt=orderRepository.findById(orderId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not exist with id "+orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orders = orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public List<Order> getAllOrders() {
		Sort sortByCreatedAtDesc = Sort.by(Sort.Direction.DESC, "createdAt");
		
		return orderRepository.findAll(sortByCreatedAtDesc);
	}

}
