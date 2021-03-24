package com.orderme.ordermebackend.model.entity;

import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity(name = "orders")
@Builder
public class Order implements AbstractEntity<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, updatable = false, nullable = false)
    private UUID orderID;

    private LocalDateTime creationTime;

    private LocalDateTime lastUpdateTime;

    @ManyToOne
    @JoinColumn(name="createdBy", referencedColumnName = "userId")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="processingBy", referencedColumnName = "userId", insertable = false)
    private User processingBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "salesOrder", fetch = FetchType.EAGER)
    @Column(insertable = false)
    private Set<OrderLine> orderLines;

    @ManyToOne
    @JoinColumn(name="shop", referencedColumnName = "shopId")
    private Shop shop;

    public Order() {
    }

    public Order(UUID orderID, LocalDateTime creationTime, LocalDateTime lastUpdateTime, User createdBy,
                 User processingBy, OrderStatus orderStatus, Set<OrderLine> orderLines, Shop shop) {
        this.orderID = orderID;
        this.creationTime = creationTime;
        this.lastUpdateTime = lastUpdateTime;
        this.createdBy = createdBy;
        this.processingBy = processingBy;
        this.orderStatus = orderStatus;
        this.orderLines = orderLines;
        this.shop = shop;
    }

    public UUID getOrderID() {
        return orderID;
    }

    public void setOrderID(UUID orderID) {
        this.orderID = orderID;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getProcessingBy() {
        return processingBy;
    }

    public void setProcessingBy(User processingBy) {
        this.processingBy = processingBy;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
