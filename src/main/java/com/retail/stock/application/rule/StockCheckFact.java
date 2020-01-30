package com.retail.stock.application.rule;

import com.retail.stock.repository.product.Product;

import static java.lang.String.format;

public class StockCheckFact {

    private final Product product;
    private int quantityRequired;

    public StockCheckFact(Product product) {
        this.product = product;
        this.quantityRequired = 0;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(int quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    @Override
    public String toString() {
        return format("StockCheckFact[product=%s,quantityRequired=%d]", product, quantityRequired);
    }
}
