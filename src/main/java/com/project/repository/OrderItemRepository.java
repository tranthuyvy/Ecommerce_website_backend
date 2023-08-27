package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.modal.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
