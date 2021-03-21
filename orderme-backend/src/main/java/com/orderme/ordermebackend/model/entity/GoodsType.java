package com.orderme.ordermebackend.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity(name = "goods_types")
public class GoodsType {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, updatable = false, nullable = false)
    private UUID goodsTypeId;

    @Column(nullable = false)
    private String title;

    private String description;

    @OneToMany(mappedBy = "goodsType")
    private Set<Goods> goods;

    public GoodsType() {
    }

    public UUID getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(UUID goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Goods> getGoods() {
        return goods;
    }

    public void setGoods(Set<Goods> goods) {
        this.goods = goods;
    }
}
