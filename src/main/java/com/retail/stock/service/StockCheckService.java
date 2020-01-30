package com.retail.stock.service;

import com.retail.stock.api.StockCheck;
import com.retail.stock.api.StockCheckApi;
import com.retail.stock.application.StockCheckApplication;
import org.springframework.stereotype.Service;

@Service
public class StockCheckService implements StockCheckApi {

    private final StockCheckApplication stockCheckApplication;

    public StockCheckService(StockCheckApplication stockCheckApplication) {
        this.stockCheckApplication = stockCheckApplication;
    }

    @Override
    public StockCheck create() {
        return stockCheckApplication.create();
    }
}
