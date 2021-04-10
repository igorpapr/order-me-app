package com.orderme.ordermebackend.service.validation.impl;

import com.orderme.ordermebackend.model.dto.GoodsAvailabilityDto;
import com.orderme.ordermebackend.service.validation.AbstractDtoValidationService;
import org.springframework.stereotype.Service;

@Service
public class GoodsAvailabilityValidationServiceImpl extends AbstractDtoValidationService<GoodsAvailabilityDto> {

    @Override
    public void validateCreate(GoodsAvailabilityDto dto) {
        if (dto != null) {
            if (dto.getAvailabilityStatus() == null) {
                failWithMissingParameter("availabilityStatus");
            }
        } else {
            failWithEmptyDto(GoodsAvailabilityDto.class);
        }
    }

    @Override
    public void validatePatch(GoodsAvailabilityDto dto) {
        if (dto != null) {
            if (dto.getAvailabilityStatus() == null) {
                failWithMissingParameter("amount");
            }
        } else {
            failWithEmptyDto(GoodsAvailabilityDto.class);
        }
    }

    @Override
    protected void failWithMissingParameter(String field) {
        failWithMissingParameter(field, GoodsAvailabilityDto.class);
    }
}
