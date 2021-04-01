package com.orderme.ordermebackend.model.dtomappers;

import com.orderme.ordermebackend.model.dto.OrderDto;
import com.orderme.ordermebackend.model.entity.Order;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    /**
     * NOTE! Composite embedded values doesn't map properly
     * -> they need to be mapped by hands
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDto(OrderDto dto, @MappingTarget Order entity);
}

