package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.OrderDto;
import com.orderme.ordermebackend.model.entity.Order;
import com.orderme.ordermebackend.service.base.PaginatedCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService extends PaginatedCrudService<UUID, Order, OrderDto> {

    Page<Order> getAllByParams(OrderDto params, boolean unprocessedOnly, Pageable pageable);

}
