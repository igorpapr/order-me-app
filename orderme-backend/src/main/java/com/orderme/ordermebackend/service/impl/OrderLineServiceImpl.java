package com.orderme.ordermebackend.service.impl;

import com.orderme.ordermebackend.model.dto.OrderLineDto;
import com.orderme.ordermebackend.model.entity.*;
import com.orderme.ordermebackend.repository.OrderLineRepository;
import com.orderme.ordermebackend.service.OrderLineService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;

    public OrderLineServiceImpl(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    @Override
    @Transactional
    public OrderLine patchByOrderIdAndGoodsId(OrderLineDto dto) {
        OrderLine orderLine =
                orderLineRepository.findById(new OrderLinesKey(dto.getOrderId(), dto.getGoodsId()))
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find order line with orderId: " + dto.getOrderId() + ", goodsId: " + dto.getGoodsId()));
        orderLine.setAmount(dto.getAmount());
        return orderLineRepository.save(orderLine);
    }

    @Override
    public void deleteByOrderIdAndGoodsId(UUID orderId, UUID goodsId) {
        orderLineRepository.deleteById(new OrderLinesKey(orderId, goodsId));
    }
}
