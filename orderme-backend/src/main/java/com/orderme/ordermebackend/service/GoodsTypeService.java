package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.GoodsTypeDto;
import com.orderme.ordermebackend.model.entity.GoodsType;
import com.orderme.ordermebackend.service.base.NonPaginatedCrudService;


public interface GoodsTypeService extends NonPaginatedCrudService<Integer, GoodsType, GoodsTypeDto> {
}
