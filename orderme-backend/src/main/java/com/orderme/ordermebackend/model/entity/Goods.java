package com.orderme.ordermebackend.model.entity;

import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
public class Goods implements AbstractEntity<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, updatable = false, nullable = false)
    private UUID goodsId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal oldPrice;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal actualPrice;

    private String imageLink;

    @OneToMany(mappedBy = "goods")
    private Set<OrderLine> orderLines;

    @ManyToOne
    @JoinColumn(name = "goods_type", referencedColumnName = "goodsTypeId", nullable = false)
    private GoodsType goodsType;

    @OneToMany(mappedBy = "goods")
    private Set<GoodsAvailability> goodsAvailabilities;

    public Goods() {
    }

    public Goods(UUID goodsId, String title, String description, BigDecimal oldPrice, BigDecimal actualPrice, String imageLink, Set<OrderLine> orderLines, GoodsType goodsType, Set<GoodsAvailability> goodsAvailabilities) {
        this.goodsId = goodsId;
        this.title = title;
        this.description = description;
        this.oldPrice = oldPrice;
        this.actualPrice = actualPrice;
        this.imageLink = imageLink;
        this.orderLines = orderLines;
        this.goodsType = goodsType;
        this.goodsAvailabilities = goodsAvailabilities;
    }

    public UUID getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(UUID goodsId) {
        this.goodsId = goodsId;
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

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public Set<GoodsAvailability> getGoodsAvailabilities() {
        return goodsAvailabilities;
    }

    public void setGoodsAvailabilities(Set<GoodsAvailability> goodsAvailabilities) {
        this.goodsAvailabilities = goodsAvailabilities;
    }

}
