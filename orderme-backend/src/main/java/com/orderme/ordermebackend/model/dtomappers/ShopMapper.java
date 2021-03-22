package com.orderme.ordermebackend.model.dtomappers;

import com.orderme.ordermebackend.model.dto.ShopDto;
import com.orderme.ordermebackend.model.entity.Shop;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateShopFromDto(ShopDto dto, @MappingTarget Shop entity);

}
