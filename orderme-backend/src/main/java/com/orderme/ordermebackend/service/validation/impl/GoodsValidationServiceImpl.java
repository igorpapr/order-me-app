package com.orderme.ordermebackend.service.validation.impl;

import com.orderme.ordermebackend.model.dto.GoodsDto;
import com.orderme.ordermebackend.service.validation.AbstractDtoValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GoodsValidationServiceImpl extends AbstractDtoValidationService<GoodsDto> {

    @Override
    public void validateCreate(GoodsDto dto) {
        if (dto != null) {
            if (dto.getTitle() == null) {
                failWithMissingParameter("title");
            }
            if (dto.getGoodsTypeId() == null) {
                failWithMissingParameter("goodsType");
            }
            if (dto.getActualPrice() == null) {
                failWithMissingParameter("actualPrice");
            }
            if (dto.getOldPrice() == null) {
                failWithMissingParameter("oldPrice");
            }

            checkPrices(dto);
        } else {
            failWithEmptyDto(GoodsDto.class);
        }
    }

    @Override
    public void validatePatch(GoodsDto dto) {
        if (dto != null) {
            checkPrices(dto);
        } else {
            failWithEmptyDto(GoodsDto.class);
        }
    }

    private void checkPrices(GoodsDto dto) {
        if (dto.getActualPrice() != null) {
            if (dto.getActualPrice().equals(BigDecimal.ZERO)) {
                throw new IllegalArgumentException("New price cannot be zero value");
            }
        }
        if (dto.getOldPrice() != null) {
            if (dto.getOldPrice().equals(BigDecimal.ZERO)) {
                throw new IllegalArgumentException("Old price cannot be zero value");
            }
        }
    }

    @Override
    protected void failWithMissingParameter(String field) {
        failWithMissingParameter(field, GoodsDto.class);
    }

}
