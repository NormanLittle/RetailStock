package com.retail.stock.application;

import com.retail.stock.api.StockCheck;
import com.retail.stock.api.StockCheckApi;
import com.retail.stock.application.rule.StockCheckFact;
import com.retail.stock.application.rule.StockCheckRuleEngine;
import com.retail.stock.application.rule.StockCheckRuleEngineFactory;
import com.retail.stock.repository.audit.Audit;
import com.retail.stock.repository.audit.AuditRepository;
import com.retail.stock.repository.product.Product;
import com.retail.stock.repository.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.retail.stock.api.StockCheck.Advice.anAdvice;
import static com.retail.stock.api.StockCheck.aStockCheck;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Component
public class StockCheckApplication implements StockCheckApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProductRepository productRepository;
    private final AuditRepository auditRepository;
    private final StockCheckRuleEngineFactory stockCheckRuleEngineFactory;

    public StockCheckApplication(ProductRepository productRepository,
                                 AuditRepository auditRepository,
                                 StockCheckRuleEngineFactory stockCheckRuleEngineFactory) {
        this.productRepository = productRepository;
        this.auditRepository = auditRepository;
        this.stockCheckRuleEngineFactory = stockCheckRuleEngineFactory;
    }

    private static LocalDate today() {
        return LocalDate.now();
    }


    @Override
    public StockCheck create() {
        List<Product> products = readProducts();
        if (isEmpty(products)) {
            return aStockCheck();
        }

        StockCheck stockCheck = processStockCheck(products);
        writeStockCheckAudit(stockCheck);

        return stockCheck;
    }

    private List<Product> readProducts() {
        logger.info("Reading all products for stock check.");
        List<Product> products = productRepository.findAll();
        if (isEmpty(products)) {
            logger.warn("No products are available for stock check.");
            return newArrayList();
        }

        logger.info("Read {} product(s) for stock check.", products.size());
        return products;
    }

    private StockCheck processStockCheck(List<Product> products) {
        logger.info("Processing stock check for {} product(s).", products.size());
        List<StockCheckFact> stockCheckFacts = products.stream()
                                                       .map(StockCheckFact::new)
                                                       .collect(toList());

        StockCheckRuleEngine stockCheckRuleEngine = stockCheckRuleEngineFactory.create();
        stockCheckRuleEngine.execute(stockCheckFacts);

        return aStockCheck(stockCheckFacts.stream()
                                          .map(stockCheckFact -> anAdvice(stockCheckFact.getProduct().getName(),
                                                                          stockCheckFact.getProduct().getQuantity(),
                                                                          stockCheckFact.getQuantityRequired()))
                                          .collect(toList()));
    }

    private void writeStockCheckAudit(StockCheck stockCheck) {
        List<StockCheck.Advice> stockCheckAdvice = stockCheck.getAdvice();

        logger.info("Writing audit entry for {} product stock check(s).", stockCheckAdvice.size());
        stockCheckAdvice.stream()
                        .map(advice -> new Audit(today(),
                                                 advice.getProduct(),
                                                 advice.getQuantityRequired()))
                        .forEach(auditRepository::save);
    }
}
