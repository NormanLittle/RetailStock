package com.retail.repository.audit;

import javax.persistence.*;
import java.time.LocalDate;

import static java.lang.String.format;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Audit")
public class Audit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "product")
    private String product;

    @Column(name = "quantityRequired")
    private int quantityRequired;

    public Audit() {
        this.date = null;
        this.product = null;
        this.quantityRequired = 0;
    }

    public Audit(LocalDate date, String product, int quantityRequired) {
        this.date = date;
        this.product = product;
        this.quantityRequired = quantityRequired;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(int quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    @Override
    public String toString() {
        return format("Audit[date=%s,product=%s,quantityRequired=%d]", date, product, quantityRequired);
    }
}
