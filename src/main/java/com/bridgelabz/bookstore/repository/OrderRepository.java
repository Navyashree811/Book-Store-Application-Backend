package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.OrderModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE bookstore.order_model SET cancel = true WHERE order_id =:orderId", nativeQuery = true)
	Integer cancelOrder(Long orderId);

	@Query(value = "SELECT * FROM bookstore.order_model where cancel=false", nativeQuery = true)
	List<OrderModel> getAllOrders();

	List<OrderModel> findAllByUser(long id);

}