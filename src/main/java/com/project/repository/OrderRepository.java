package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.modal.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = PLACED OR o.orderStatus = CONFIRMED OR o.orderStatus = SHIPPED OR o.orderStatus = DELIVERED) ORDER BY o.createdAt DESC")
	public List<Order> getUsersOrders(@Param("userId") Long userId);

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = PLACED) ORDER BY o.createdAt DESC")
	public List<Order> getPlacedOrders(@Param("userId") Long userId);

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = CONFIRMED) ORDER BY o.createdAt DESC")
	public List<Order> getConfirmedOrders(@Param("userId") Long userId);

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = SHIPPED) ORDER BY o.createdAt DESC")
	public List<Order> getShippedOrders(@Param("userId") Long userId);

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = DELIVERED) ORDER BY o.createdAt DESC")
	public List<Order> getDeliveredOrders(@Param("userId") Long userId);

	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = CANCELLED) ORDER BY o.createdAt DESC")
	public List<Order> getCancelledOrders(@Param("userId") Long userId);
	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = SUCCESS) ORDER BY o.createdAt DESC")
	public List<Order> getSuccessOrders(@Param("userId") Long userId);
}
