package com.orderme.ordermebackend.model.dto;

import com.orderme.ordermebackend.model.entity.AvailabilityStatus;

public class GoodsAvailabilityDto implements AbstractDto {

    private AvailabilityStatus availabilityStatus;

    public GoodsAvailabilityDto() {
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
