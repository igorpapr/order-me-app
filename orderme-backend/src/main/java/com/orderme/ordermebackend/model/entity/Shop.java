package com.orderme.ordermebackend.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "shops")
public class Shop {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private int shopId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "shop")
    private Set<GoodsAvailability> goodsAvailabilities;

    public Shop() {
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<GoodsAvailability> getGoodsAvailabilities() {
        return goodsAvailabilities;
    }

    public void setGoodsAvailabilities(Set<GoodsAvailability> goodsAvailabilities) {
        this.goodsAvailabilities = goodsAvailabilities;
    }
}
