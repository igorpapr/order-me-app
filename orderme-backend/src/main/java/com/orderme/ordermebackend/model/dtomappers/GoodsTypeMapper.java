package com.orderme.ordermebackend.model.dtomappers;

import com.orderme.ordermebackend.model.dto.GoodsTypeDto;
import com.orderme.ordermebackend.model.entity.GoodsType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GoodsTypeMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGoodsTypeFromDto(GoodsTypeDto dto, @MappingTarget GoodsType entity);

}
