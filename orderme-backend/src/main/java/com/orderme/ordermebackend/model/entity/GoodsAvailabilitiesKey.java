package com.orderme.ordermebackend.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class GoodsAvailabilitiesKey implements Serializable {

    @Column(name = "shopId")
    private Integer shopId;

    @Column(name = "goodsId")
    private UUID goodsId;

    public GoodsAvailabilitiesKey() {
    }

    public GoodsAvailabilitiesKey(Integer shopId, UUID goodsId) {
        this.shopId = shopId;
        this.goodsId = goodsId;
    }

    public UUID getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(UUID goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
