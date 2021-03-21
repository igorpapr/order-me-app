package com.orderme.ordermebackend.model.dtomappers;

import com.orderme.ordermebackend.model.dto.GoodsDto;
import com.orderme.ordermebackend.model.entity.Goods;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GoodsMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGoodsFromDto(GoodsDto dto, @MappingTarget Goods entity);
}
