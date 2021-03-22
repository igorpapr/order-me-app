package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.GoodsAvailabilityDto;
import com.orderme.ordermebackend.model.entity.GoodsAvailability;

import java.util.UUID;

public interface GoodsAvailabilityService {

    GoodsAvailability getByShopIdAndGoodsId(Integer shopId, UUID goodsId);

    GoodsAvailability createByShopIdAndGoodsId(GoodsAvailabilityDto dto,
                                               Integer shopId, UUID goodsId);
    GoodsAvailability patchByShopIdAndGoodsId(GoodsAvailabilityDto dto,
                                              Integer shopId, UUID goodsId);
    void deleteByShopIdAndGoodsId(Integer shopId, UUID goodsId);
}
