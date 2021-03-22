package com.orderme.ordermebackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "shops")
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"}) //https://question-it.com/questions/533939/ne-najden-serializator-dlja-klassa-orghibernateproxypojobytebuddybytebuddyinterceptor-i-ne-najdeny-svojstva-dlja-sozdanija-beanserializer
public class Shop implements AbstractEntity<Integer> {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private Integer shopId;

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "shop")
    @JsonIgnore
    private Set<GoodsAvailability> goodsAvailabilities;

    public Shop() {
    }

    public Shop(Integer shopId, String address, String title, Set<GoodsAvailability> goodsAvailabilities) {
        this.shopId = shopId;
        this.address = address;
        this.title = title;
        this.goodsAvailabilities = goodsAvailabilities;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
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
