package com.orderme.ordermebackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import javax.persistence.*;

@Entity(name = "goods_availabilities")
@JsonIgnoreProperties({"hibernateLazyInitializer"}) //https://question-it.com/questions/533939/ne-najden-serializator-dlja-klassa-orghibernateproxypojobytebuddybytebuddyinterceptor-i-ne-najdeny-svojstva-dlja-sozdanija-beanserializer
@Builder
public class GoodsAvailability implements AbstractEntity<GoodsAvailabilitiesKey> {

    @EmbeddedId
    private GoodsAvailabilitiesKey goodsAvailabilitiesId;

    @JsonIgnore
    @ManyToOne
    @MapsId("goodsId")
    @JoinColumn(name = "goods", nullable = false)
    private Goods goods;

    @JsonIgnore
    @ManyToOne
    @MapsId("shopId")
    @JoinColumn(name = "shop", nullable = false)
    private Shop shop;
//
//    @Column(nullable = false)
//    private int amount = 0;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    public GoodsAvailability() {
    }

    public GoodsAvailability(GoodsAvailabilitiesKey goodsAvailabilitiesId, Goods goods, Shop shop, AvailabilityStatus status) {
        this.goodsAvailabilitiesId = goodsAvailabilitiesId;
        this.goods = goods;
        this.shop = shop;
        this.availabilityStatus = status;
//        this.amount = amount;
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

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    //    public int getAmount() {
//        return amount;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }
}
