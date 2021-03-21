package com.orderme.ordermebackend.service.validation.impl;

import com.orderme.ordermebackend.model.dto.GoodsDto;
import com.orderme.ordermebackend.service.validation.AbstractDtoValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GoodsValidationServiceImpl extends AbstractDtoValidationService<GoodsDto> {

    @Override
    public void validateCreate(GoodsDto dto) {
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
    }

    @Override
    public void validatePatch(GoodsDto dto) {
        checkPrices(dto);
    }

    private void checkPrices(GoodsDto dto) {
        if (dto.getActualPrice().equals(BigDecimal.ZERO) || dto.getOldPrice().equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("Price cannot be zero value");
        }
    }

//    @Override
//    public void validateHasOwnId(GoodsDto dto) {
//        if (dto.getGoodsId() == null) {
//            failWithMissingId(GoodsDto.class);
//        }
//    }

    @Override
    protected void failWithMissingParameter(String field) {
        failWithMissingParameter(field, GoodsDto.class);
    }
}
