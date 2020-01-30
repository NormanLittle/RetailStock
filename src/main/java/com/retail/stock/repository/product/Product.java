package com.retail.stock.repository.product;

import javax.persistence.*;

import static java.lang.String.format;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    public Product() {
        this.name = null;
        this.quantity = 0;
    }

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static Product aProduct() {
        return new Product();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product withName(String name) {
        setName(name);
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product withQuantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    @Override
    public String toString() {
        return format("Product[name=%s,quantity=%d]", name, quantity);
    }
}
