package com.orderme.ordermebackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import javax.persistence.*;

@Entity(name = "order_lines")
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"}) //https://question-it.com/questions/533939/ne-najden-serializator-dlja-klassa-orghibernateproxypojobytebuddybytebuddyinterceptor-i-ne-najdeny-svojstva-dlja-sozdanija-beanserializer
public class OrderLine implements AbstractEntity<OrderLinesKey> {

    @EmbeddedId
    private OrderLinesKey orderLineId;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "salesOrder", nullable = false)
    @JsonIgnore
    private Order salesOrder;

    @ManyToOne
    @MapsId("goodsId")
    @JoinColumn(name = "goods", nullable = false)
//    @JsonIgnore
    private Goods goods;

    @Column(nullable = false)
    private int amount;

    public OrderLine() {
    }

    public OrderLine(OrderLinesKey orderLineId, Order salesOrder, Goods goods, int amount) {
        this.orderLineId = orderLineId;
        this.salesOrder = salesOrder;
        this.goods = goods;
        this.amount = amount;
    }

    public OrderLinesKey getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(OrderLinesKey orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Order getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(Order salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "orderLineId=" + orderLineId +
                ", amount=" + amount +
                '}';
    }
}
