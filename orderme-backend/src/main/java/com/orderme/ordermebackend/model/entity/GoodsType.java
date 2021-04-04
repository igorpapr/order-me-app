package com.orderme.ordermebackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "goods_types")
@JsonIgnoreProperties({"hibernateLazyInitializer"}) //https://question-it.com/questions/533939/ne-najden-serializator-dlja-klassa-orghibernateproxypojobytebuddybytebuddyinterceptor-i-ne-najdeny-svojstva-dlja-sozdanija-beanserializer
@Builder
public class GoodsType implements AbstractEntity<Integer> {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private Integer goodsTypeId;

    @Column(nullable = false)
    private String title;

    private String description;

    private String imageLink;

    @JsonIgnore
    @OneToMany(mappedBy = "goodsType", cascade = CascadeType.ALL)
    private Set<Goods> goods;

    public GoodsType() {
    }

    public GoodsType(Integer goodsTypeId, String title, String description, String imageLink, Set<Goods> goods) {
        this.goodsTypeId = goodsTypeId;
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.goods = goods;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Set<Goods> getGoods() {
        return goods;
    }

    public void setGoods(Set<Goods> goods) {
        this.goods = goods;
    }

}
