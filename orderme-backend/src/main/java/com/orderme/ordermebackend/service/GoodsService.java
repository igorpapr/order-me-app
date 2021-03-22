package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.GoodsDto;
import com.orderme.ordermebackend.model.entity.Goods;
import com.orderme.ordermebackend.service.base.PaginatedCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GoodsService extends PaginatedCrudService<UUID, Goods, GoodsDto> {

    Page<Goods> getByGoodsTypeId(int goodsTypeId, Pageable pageable);

}
