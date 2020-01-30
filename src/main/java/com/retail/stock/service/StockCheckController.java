package com.retail.stock.service;

import com.retail.stock.api.StockCheck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stock")
public class StockCheckController {

    private final StockCheckService stockCheckService;

    public StockCheckController(StockCheckService stockCheckService) {
        this.stockCheckService = stockCheckService;
    }

    @PostMapping(value = "/check")
    @ResponseBody
    public ResponseEntity<StockCheck> createStockCheck() {
        return new ResponseEntity<>(stockCheckService.create(), HttpStatus.CREATED);
    }
}
