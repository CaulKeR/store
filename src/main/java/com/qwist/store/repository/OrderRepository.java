package com.qwist.store.repository;

import com.qwist.store.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author - Kiryl Karpuk
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

}
