package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.modal.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = PLACED OR o.orderStatus = CONFIRMED OR o.orderStatus = SHIPPED OR o.orderStatus = DELIVERED)")
	public List<Order> getUsersOrders(@Param("userId") Long userId);

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = PLACED)")
	public List<Order> getPlacedOrders(@Param("userId") Long userId);

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = CONFIRMED)")
	public List<Order> getConfirmedOrders(@Param("userId") Long userId);

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = SHIPPED)")
	public List<Order> getShippedOrders(@Param("userId") Long userId);

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = DELIVERED)")
	public List<Order> getDeliveredOrders(@Param("userId") Long userId);
}
