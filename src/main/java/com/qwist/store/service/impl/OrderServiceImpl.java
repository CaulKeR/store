package com.qwist.store.service.impl;

import com.qwist.store.model.Order;
import com.qwist.store.repository.OrderRepository;
import com.qwist.store.service.OrderService;
import com.qwist.store.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/***
 * @author - Kiryl Karpuk
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public Optional<Order> createOrder(Order order) {
        order.setId(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
        return Optional.of(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
