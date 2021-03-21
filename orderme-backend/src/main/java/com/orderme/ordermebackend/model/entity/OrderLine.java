package com.orderme.ordermebackend.model.entity;

import javax.persistence.*;

@Entity(name = "order_lines")
public class OrderLine {

    @EmbeddedId
    private OrderLinesKey orderLineId;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "salesOrder", nullable = false)
    private Order salesOrder;

    @ManyToOne
    @MapsId("goodsId")
    @JoinColumn(name = "goods", nullable = false)
    private Goods goods;

    @Column(nullable = false)
    private int amount;

    public OrderLine() {
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
}
