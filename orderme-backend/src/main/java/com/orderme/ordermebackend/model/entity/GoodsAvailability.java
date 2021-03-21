package com.orderme.ordermebackend.model.entity;

import javax.persistence.*;

@Entity(name = "goods_availabilities")
public class GoodsAvailability implements AbstractEntity {

    @EmbeddedId
    private GoodsAvailabilitiesKey goodsAvailabilitiesId;

    @ManyToOne
    @MapsId("goodsId")
    @JoinColumn(name = "goods", nullable = false)
    private Goods goods;

    @ManyToOne
    @MapsId("shopId")
    @JoinColumn(name = "shop", nullable = false)
    private Shop shop;

    @Column(nullable = false)
    private int amount = 0;

    public GoodsAvailability() {
    }

    public GoodsAvailabilitiesKey getGoodsAvailabilitiesId() {
        return goodsAvailabilitiesId;
    }

    public void setGoodsAvailabilitiesId(GoodsAvailabilitiesKey goodsAvailabilitiesId) {
        this.goodsAvailabilitiesId = goodsAvailabilitiesId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
