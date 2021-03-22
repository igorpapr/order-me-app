package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.ShopDto;
import com.orderme.ordermebackend.model.entity.Shop;
import com.orderme.ordermebackend.service.base.NonPaginatedCrudService;

public interface ShopService extends NonPaginatedCrudService<Integer, Shop, ShopDto> {
}
