package com.orderme.ordermebackend.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class GoodsAvailabilitiesKey implements Serializable {

    @Column(name = "shopId")
    private int shopId;

    @Column(name = "goodsId")
    private UUID goodsId;

    public GoodsAvailabilitiesKey() {
    }

    public UUID getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(UUID goodsId) {
        this.goodsId = goodsId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
