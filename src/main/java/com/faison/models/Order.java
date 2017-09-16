package com.faison.models;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    private double cost;
    @ManyToOne(targetEntity = User.class, optional = false)
    private User buyer;
    @Column(nullable = false, updatable = false)
    private Date placedOn;
    private double shippingFee;
    @OneToMany(targetEntity = OrderItem.class)
    private List<OrderItem> orderItemList;
    private boolean deliveredStatus;
    @ManyToOne(targetEntity = Address.class, optional = false)
    private Address shippingAddress;

    public String getId() {
        return id;
    }

    public Order setId(String id) {
        this.id = id;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public Order setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public User getBuyer() {
        return buyer;
    }

    public Order setBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    public Date getPlacedOn() {
        return placedOn;
    }

    public Order setPlacedOn(Date placedOn) {
        this.placedOn = placedOn;
        return this;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public Order setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
        return this;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public Order setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
        return this;
    }

    public boolean isDeliveredStatus() {
        return deliveredStatus;
    }

    public Order setDeliveredStatus(boolean deliveredStatus) {
        this.deliveredStatus = deliveredStatus;
        return this;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public Order setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }
}
