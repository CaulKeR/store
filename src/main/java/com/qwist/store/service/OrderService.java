package com.qwist.store.service;

import com.qwist.store.model.Order;

import java.util.List;
import java.util.Optional;

/***
 * @author - Kiryl Karpuk
 */
public interface OrderService {

    List<Order> findAll();

    List<Order> findOrders(Long customerId);

    Optional<Order> createOrder(Order order);

    void deleteOrder(Long orderId);

}
