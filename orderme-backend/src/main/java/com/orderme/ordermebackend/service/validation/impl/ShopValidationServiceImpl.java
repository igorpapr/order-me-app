package com.orderme.ordermebackend.service.validation.impl;

import com.orderme.ordermebackend.model.dto.ShopDto;
import com.orderme.ordermebackend.service.validation.AbstractDtoValidationService;
import org.springframework.stereotype.Service;

@Service
public class ShopValidationServiceImpl extends AbstractDtoValidationService<ShopDto> {
    @Override
    protected void failWithMissingParameter(String field) {
        failWithMissingParameter(field, ShopDto.class);
    }

    @Override
    public void validateCreate(ShopDto dto) {
        if (dto != null) {
            if (dto.getTitle() == null) {
                failWithMissingParameter("title");
            }
        } else {
            failWithEmptyDto(ShopDto.class);
        }
    }

    @Override
    public void validatePatch(ShopDto dto) {
    }
}
