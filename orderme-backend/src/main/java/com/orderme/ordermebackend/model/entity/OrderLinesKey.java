package com.orderme.ordermebackend.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class OrderLinesKey implements Serializable {

    @Column(name = "orderId")
    private UUID orderId;

    @Column(name = "goodsId")
    private UUID goodsId;

    public OrderLinesKey() {
    }

    public OrderLinesKey(UUID orderId, UUID goodsId) {
        this.orderId = orderId;
        this.goodsId = goodsId;
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
}
