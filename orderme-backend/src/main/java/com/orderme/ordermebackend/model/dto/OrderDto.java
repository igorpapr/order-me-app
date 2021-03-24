package com.orderme.ordermebackend.model.dto;

import com.orderme.ordermebackend.model.entity.OrderLine;
import com.orderme.ordermebackend.model.entity.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public class OrderDto implements AbstractDto{

    //SHOULD NOT BE USED FOR MAPPING
    private UUID orderId;

    private OrderStatus orderStatus;

    private UUID createdById;

    private UUID processingById;

    private Set<OrderLineDto> orderLines;

    private Integer shopId;

    public OrderDto() {
    }

    public OrderDto(UUID orderId, OrderStatus orderStatus, UUID createdById, UUID processingById, Set<OrderLineDto> orderLines, Integer shopId) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.createdById = createdById;
        this.processingById = processingById;
        this.orderLines = orderLines;
        this.shopId = shopId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public UUID getProcessingById() {
        return processingById;
    }

    public void setProcessingById(UUID processingById) {
        this.processingById = processingById;
    }

    public Set<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
