package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.GoodsAvailabilityDto;
import com.orderme.ordermebackend.model.dto.OrderLineDto;
import com.orderme.ordermebackend.model.entity.GoodsAvailability;
import com.orderme.ordermebackend.model.entity.Order;
import com.orderme.ordermebackend.model.entity.OrderLine;

import java.util.UUID;

public interface OrderLineService {
    OrderLine getByOrderIdAndGoodsId(UUID orderId, UUID goodsId);

    OrderLine createByOrderIdAndGoodsId(OrderLineDto dto,
                                        UUID orderId, UUID goodsId);
}
