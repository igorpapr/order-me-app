package com.orderme.ordermebackend.model.dtomappers;

import com.orderme.ordermebackend.model.dto.GoodsAvailabilityDto;
import com.orderme.ordermebackend.model.entity.GoodsAvailability;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GoodsAvailabilityMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGoodsAvailabilityFromDto(GoodsAvailabilityDto dto, @MappingTarget GoodsAvailability entity);

}