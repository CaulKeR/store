package com.qwist.store.controller;

import com.qwist.store.exception.CustomerRestrictedActionException;
import com.qwist.store.exception.ResourceNotFoundException;
import com.qwist.store.model.Order;
import com.qwist.store.model.User;
import com.qwist.store.service.OrderService;
import com.qwist.store.service.UserService;
import enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/***
 * @author - Kiryl Karpuk
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping(path = {"", "/{customerId}"})
    public ResponseEntity<List<Order>> getOrders(@AuthenticationPrincipal UserDetails userDetails,
                                                 @PathVariable(required = false) Long customerId) {
        validateRequest(userDetails, customerId);
        if (customerId == null) {
            return ResponseEntity.ok(orderService.findAll());
        }
        return ResponseEntity.ok(orderService.findOrders(customerId));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody Order order) {
        validateRequest(userDetails, order.getCustomerId());
        return ResponseEntity.of(orderService.createOrder(order));
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    private void validateRequest(@AuthenticationPrincipal UserDetails userDetails,
                                 Long customerId) {
        if (isCustomer(userDetails)) {
            User user =
                    userService
                            .findByUsername(userDetails.getUsername())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("User not found"));
            if (!Objects.equals(customerId, user.getId())) {
                throw new CustomerRestrictedActionException(
                        "You can't do anything with orders of another customer");
            }
        }
    }

    private boolean isCustomer(UserDetails userDetails) {
        return userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority ->
                        authority.equals(UserRole.ROLE_CUSTOMER.name()));
    }

}
