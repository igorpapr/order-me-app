package com.orderme.ordermebackend.model.dto;

import java.util.UUID;

public class OrderLineDto implements AbstractDto {

    private UUID orderId;

    private UUID goodsId;

    private int amount;

    public OrderLineDto() {
    }

    public OrderLineDto(UUID orderId, UUID goodsId, int amount) {
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.amount = amount;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(UUID goodsId) {
        this.goodsId = goodsId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderLineDto{" +
                "orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", amount=" + amount +
                '}';
    }
}
