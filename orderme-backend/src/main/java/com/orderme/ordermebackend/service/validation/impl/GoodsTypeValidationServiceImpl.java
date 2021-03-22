package com.orderme.ordermebackend.service.validation.impl;

import com.orderme.ordermebackend.model.dto.GoodsTypeDto;
import com.orderme.ordermebackend.service.validation.AbstractDtoValidationService;
import org.springframework.stereotype.Service;

@Service
public class GoodsTypeValidationServiceImpl extends AbstractDtoValidationService<GoodsTypeDto> {

    @Override
    public void validateCreate(GoodsTypeDto dto) {
        if (dto != null) {
            if (dto.getTitle() == null) {
                failWithMissingParameter("title");
            }
        } else {
            failWithEmptyDto(GoodsTypeDto.class);
        }
    }

    @Override
    public void validatePatch(GoodsTypeDto dto) {
    }

    @Override
    protected void failWithMissingParameter(String field) {
        failWithMissingParameter(field, GoodsTypeDto.class);
    }
}
