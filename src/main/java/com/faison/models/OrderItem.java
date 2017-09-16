package com.faison.models;

import javax.persistence.*;

@Entity
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    @ManyToOne(targetEntity = Product.class, optional = false)
    private Product product;
    private double quantity;
    private double cost;

    public String getId() {
        return id;
    }

    public OrderItem setId(String id) {
        this.id = id;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public OrderItem setProduct(Product product) {
        this.product = product;
        return this;
    }

    public double getQuantity() {
        return quantity;
    }

    public OrderItem setQuantity(double quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public OrderItem setCost(double cost) {
        this.cost = cost;
        return this;
    }
}
