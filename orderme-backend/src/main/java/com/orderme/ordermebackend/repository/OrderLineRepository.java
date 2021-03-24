package com.orderme.ordermebackend.repository;

import com.orderme.ordermebackend.model.entity.OrderLine;
import com.orderme.ordermebackend.model.entity.OrderLinesKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLinesKey> {
}
