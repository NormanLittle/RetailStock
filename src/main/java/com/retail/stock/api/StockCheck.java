package com.retail.stock.api;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;

public class StockCheck {

    private final List<Advice> advice;

    private StockCheck(List<Advice> advice) {
        this.advice = advice;
    }

    public static StockCheck aStockCheck() {
        return new StockCheck(newArrayList());
    }

    public static StockCheck aStockCheck(List<Advice> advice) {
        return new StockCheck(advice);
    }

    public List<Advice> getAdvice() {
        return advice;
    }

    @Override
    public String toString() {
        return format("StockCheck[advice=%s]", advice);
    }

    public static class Advice {
        private final String product;
        private final int quantity;
        private final int quantityRequired;

        private Advice(String product, int quantity, int quantityRequired) {
            this.product = product;
            this.quantity = quantity;
            this.quantityRequired = quantityRequired;
        }

        public static Advice anAdvice(String product, int quantity, int quantityRequired) {
            return new Advice(product, quantity, quantityRequired);
        }

        public String getProduct() {
            return product;
        }

        @SuppressWarnings("unused")
        public int getQuantity() {
            return quantity;
        }

        public int getQuantityRequired() {
            return quantityRequired;
        }

        @Override
        public String toString() {
            return format("Advice[product=%s;quantity=%d,quantityRequired=%d", product, quantity, quantityRequired);
        }
    }
}
