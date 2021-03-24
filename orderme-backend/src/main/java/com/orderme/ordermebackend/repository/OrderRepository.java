package com.orderme.ordermebackend.repository;

import com.orderme.ordermebackend.model.entity.Order;
import com.orderme.ordermebackend.model.entity.OrderStatus;
import com.orderme.ordermebackend.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>, QueryByExampleExecutor<Order> {
//
//    Page<Order> findAllByCreatedBy(User createdBy, Pageable pageable);
//
//    Page<Order> findAllByProcessingBy(User processingBy, Pageable pageable);
//
//    Page<Order> findAllByOrderStatus(OrderStatus orderStatus, Pageable pageable);

//      //getAllOrdersByShopOptionalStatus(RequestParam required false / OR OPTIONAL<OrderStatus>)
//    //getAllOrdersByCreatedByIdOptionalStatus(RequestParam required false / OR OPTIONAL<OrderStatus>)
//    //getOrderById
//    //patchOrderById
//    //deleteOrderById -- CANNOT DELETE WHEN WAITING FOR PROCESSING
//
//    //pageable - page, size https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#core.web
//    @GetMapping
//    public ResponseEntity<?> getAllOrdersByPageable(@RequestParam(required = false) Integer shopId,
//                                                    @RequestParam(required = false) UUID createdBy,
//                                                    @RequestParam(required = false) UUID processingBy,
//                                                    @RequestParam(required = false) OrderStatus status,
//                                                    Pageable pageable) {
//

}
