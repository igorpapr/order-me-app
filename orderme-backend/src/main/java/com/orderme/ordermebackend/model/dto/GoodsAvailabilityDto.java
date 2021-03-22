package com.orderme.ordermebackend.model.dto;

public class GoodsAvailabilityDto implements AbstractDto {

    private Integer amount;

    public GoodsAvailabilityDto() {
    }

    public GoodsAvailabilityDto(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
