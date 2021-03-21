package com.orderme.ordermebackend.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public class GoodsDto implements AbstractDto {

    private String title;

    private String description;

    private BigDecimal oldPrice;

    private BigDecimal actualPrice;

    private String imageLink;

    private Integer goodsTypeId;

    public GoodsDto() {
    }

    public GoodsDto(String title, String description, BigDecimal oldPrice, BigDecimal actualPrice, String imageLink, Integer goodsTypeId) {
        this.title = title;
        this.description = description;
        this.oldPrice = oldPrice;
        this.actualPrice = actualPrice;
        this.imageLink = imageLink;
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

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }
}
