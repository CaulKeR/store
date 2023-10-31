package com.qwist.store.service.impl;

import com.qwist.store.model.Order;
import com.qwist.store.repository.OrderRepository;
import com.qwist.store.service.SequenceGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order1;
    private Order order2;

    @BeforeEach
    public void setUp() {
        order1 = new Order();
        order1.setId(1L);
        order1.setCustomerId(1L);
        order1.setProducts(Arrays.asList("Product1", "Product2"));

        order2 = new Order();
        order2.setId(2L);
        order2.setCustomerId(2L);
        order2.setProducts(Arrays.asList("Product3", "Product4"));
    }

    @Test
    public void testFindAllWhenOrdersExistThenReturnAllOrders() {
        List<Order> expectedOrders = Arrays.asList(order1, order2);
        when(orderRepository.findAll()).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.findAll();

        assertThat(actualOrders).isEqualTo(expectedOrders);
    }

    @Test
    public void testFindAllWhenNoOrdersExistThenReturnEmptyList() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<Order> actualOrders = orderService.findAll();

        assertThat(actualOrders).isEmpty();
    }

    @Test
    public void testFindOrdersWhenOrdersExistThenReturnOrders() {
        List<Order> expectedOrders = Collections.singletonList(order1);
        when(orderRepository.findByCustomerId(1L)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.findOrders(1L);

        assertThat(actualOrders).isEqualTo(expectedOrders);
    }

    @Test
    public void testFindOrdersWhenNoOrdersExistThenReturnEmptyList() {
        when(orderRepository.findByCustomerId(1L)).thenReturn(Collections.emptyList());

        List<Order> actualOrders = orderService.findOrders(1L);

        assertThat(actualOrders).isEmpty();
    }

    @Test
    public void testCreateOrderWhenOrderIsCreatedThenReturnOptionalWithOrder() {
        when(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME)).thenReturn(1L);
        when(orderRepository.save(order1)).thenReturn(order1);

        Optional<Order> actualOrder = orderService.createOrder(order1);

        assertThat(actualOrder).isPresent().contains(order1);
    }

    @Test
    public void testDeleteOrderWhenValidOrderIdThenOrderIsDeleted() {
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteOrderWhenInvalidOrderIdThenOrderIsNotDeleted() {
        doThrow(new IllegalArgumentException()).when(orderRepository).deleteById(1L);

        try {
            orderService.deleteOrder(1L);
        } catch (IllegalArgumentException e) {
            verify(orderRepository, times(1)).deleteById(1L);
        }
    }

}
