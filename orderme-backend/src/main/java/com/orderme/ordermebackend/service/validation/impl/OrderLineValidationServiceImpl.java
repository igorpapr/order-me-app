package com.orderme.ordermebackend.service.validation.impl;

import com.orderme.ordermebackend.model.dto.OrderLineDto;
import com.orderme.ordermebackend.service.validation.AbstractDtoValidationService;
import org.springframework.stereotype.Service;

@Service
public class OrderLineValidationServiceImpl extends AbstractDtoValidationService<OrderLineDto> {
    @Override
    protected void failWithMissingParameter(String field) {
        failWithMissingParameter(field, OrderLineDto.class);
    }

    @Override
    public void validateCreate(OrderLineDto dto) {
    }

    @Override
    public void validatePatch(OrderLineDto dto) {
        if (dto != null){
            if (dto.getOrderId() == null) {
                failWithMissingParameter("orderId");
            }
            if (dto.getGoodsId() == null) {
                failWithMissingParameter("goodsId");
            }
            if (dto.getAmount() <= 0) {
                throw new IllegalArgumentException("The field 'amount' must be greater than 0. Current amount: " + dto.getAmount());
            }
        } else {
            failWithEmptyDto(OrderLineDto.class);
        }
    }
}
