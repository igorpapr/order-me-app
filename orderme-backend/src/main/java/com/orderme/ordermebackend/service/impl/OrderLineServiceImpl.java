package com.orderme.ordermebackend.service.impl;

import com.orderme.ordermebackend.model.dto.OrderLineDto;
import com.orderme.ordermebackend.model.entity.*;
import com.orderme.ordermebackend.repository.OrderLineRepository;
import com.orderme.ordermebackend.service.OrderLineService;

import javax.persistence.EntityExistsException;
import java.util.UUID;

public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;

    public OrderLineServiceImpl(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    @Override
    public OrderLine getByOrderIdAndGoodsId(UUID orderId, UUID goodsId) {
        return null;
    }

    @Override
    public OrderLine createByOrderIdAndGoodsId(OrderLineDto dto, UUID orderId, UUID goodsId) {
//        OrderLine orderLine;
//        OrderLinesKey id = new OrderLinesKey(orderId, goodsId);
//
//        if (!orderLineRepository.existsById(id)) {
//            Goods goodsRef = goodsRepository.getOne(goodsId);
//            Shop shopRef = shopRepository.getOne(shopId);
//            goodsAvailability = GoodsAvailability.builder()
//                    .goodsAvailabilitiesId(id)
//                    .amount(dto.getAmount())
//                    .goods(goodsRef)
//                    .shop(shopRef)
//                    .build();
//        } else {
//            throw new EntityExistsException("The goods availability with shopId: " + shopId +
//                    " and goodsId: " + goodsId + " already exists.");
//        }
        //return goodsAvailabilityRepository.save(goodsAvailability);
        return null;
    }
}
