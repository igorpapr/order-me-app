package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.OrderLineDto;
import com.orderme.ordermebackend.model.entity.OrderLine;

import java.util.UUID;

public interface OrderLineService {
    OrderLine patchByOrderIdAndGoodsId(OrderLineDto dto);

    void deleteByOrderIdAndGoodsId(UUID orderId, UUID goodsId);
}
