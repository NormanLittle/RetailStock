package com.retail.application.stock;

import com.retail.repository.product.Product;

import static java.lang.String.format;

public interface StockApi {

    void executeStockCheck();

    class StockCheck {
        private final Product product;
        private int quantityRequired;

        public StockCheck(Product product) {
            this.product = product;
            this.quantityRequired = 0;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantityRequired() {
            return quantityRequired;
        }

        @SuppressWarnings("unused")
        public void setQuantityRequired(int quantityRequired) {
            this.quantityRequired = quantityRequired;
        }

        @Override
        public String toString() {
            return format("StockCheck[product=%s,quantityRequired=%d]", product, quantityRequired);
        }
    }
}
